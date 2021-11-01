package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.repositories.PharmacyRepository;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

// Integration test
@SpringBootTest
@AutoConfigureMockMvc
// встановлюємо на test Profile, для того щоб спрінг не ініціалізовував всі біни (не трогав наші конфіги для postgres бд)
@ActiveProfiles("test")
public class PharmacyControllerIT {

    private MockMvc mockMvc;
    private DatabaseDataSourceConnection dataSourceConnection;
//    private PharmacyRepository pharmacyRepository;

    @Autowired
    public void setComponents(final MockMvc mockMvc,
                              final DataSource dataSource) throws SQLException {
        this.mockMvc = mockMvc;
        this.dataSourceConnection = new DatabaseDataSourceConnection(dataSource);
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void findAllPharmacies_findIds_ok() throws Exception {
        try {
            DatabaseOperation.REFRESH.execute(this.dataSourceConnection, readDataSet());
            this.mockMvc.perform(MockMvcRequestBuilders.get("/pharmacies"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
//                    .andExpect(MockMvcResultMatchers.content().json("""
//                            [
//                                { "id": 2021102101, "name": PharmacyControllerIT_dataset1, "medicineLinkTemplate": null},
//                                { "id": 2021102102, "name": PharmacyControllerIT_dataset2, "medicineLinkTemplate": null}
//                            ]"""));
            .andExpect(
                    MockMvcResultMatchers.jsonPath(
                            "$[*].id",
                            Matchers.hasItems(
                                    2021102101,
                                    2021102102)));
        } finally {
            this.dataSourceConnection.close();
        }
    }

    private IDataSet readDataSet() throws DataSetException, IOException {
        try (InputStream resource =
                     getClass()
                             .getResourceAsStream("PharmacyControllerIT_dataset.xml")) {
            return new FlatXmlDataSetBuilder()
                    .build(resource);
        }
    }

}
