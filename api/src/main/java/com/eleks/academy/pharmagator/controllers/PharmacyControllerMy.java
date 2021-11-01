package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.projection.PharmacyLight;
import com.eleks.academy.pharmagator.repositories.PharmacyRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

//@Controller
// В нас всі контролери є рест контролерами, тому є сенс їх поміняти на RestController
// Різниця між ними в тому, що не явно біля методів ставить анотацію @ResponseBody
// Це гарантує те що, любий метод який буде щось повертати він буде ResponseBody
//@RestController
//FIXME всюди поставити @RestController
@RequiredArgsConstructor
//@RequestMapping("/pharmaciesmy")
public class PharmacyControllerMy {

    private final ProjectionFactory projectionFactory;
    private final PharmacyRepository pharmacyRepository;


    @GetMapping
    public List<PharmacyLight> getAll() {
        //FIXME випрвити, ResponseEntity має сенс повертати, коли ми шукаємо по ІД,
        // для ліста достатньо повернути сам ліст. (Тобто не врапити)
        //return ResponseEntity.ok(pharmacyRepository.findAll());

//        this.pharmacyRepository.findAll()
//                .stream()
//                .map(entity -> this.projectionFactory.createProjection(PharmacyLight.class, entity))
//                .collect(Collectors.toList());
        return this.pharmacyRepository.findAllLight();
    }

    @GetMapping(value = "/{id:[\\d]+}")
    public ResponseEntity<Pharmacy> getById(@PathVariable Long id) {
        return pharmacyRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Pharmacy> create(@RequestBody Pharmacy pharmacy) {
        //FIXME неможна давати клієнтам можливість маніпулювати ID
        //FIXME занулювати айди якшо немає часу писати ДТО
        // И на створення не потрібно теж роботи ResponseEntity
        pharmacy.setId(null);
        if (pharmacyRepository.findById(pharmacy.getId()).isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(pharmacyRepository.save(pharmacy));
    }

    // @Valid означае що будуть перевирени вси анотации валидации
    @PutMapping("/{id:[\\d]+}")
    public ResponseEntity<Pharmacy> update(@PathVariable Long id,
                                           @Valid @RequestBody PharmacyRequest pharmacy) {
//        return pharmacyRepository.findById(id)
//                .map(ph -> ph.getId() == pharmacy.getId() ?
//                        pharmacyRepository.save(pharmacy) :
//                        pharmacyRepository.save(ph))
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
        return pharmacyRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id:[\\d]+}")
    public ResponseEntity<Pharmacy> deleteById(@PathVariable Long id) {
        pharmacyRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class PharmacyRequest {

    //@NotBlank не буде пропускати пусті строки
    @NotEmpty
    private String name;
    @NotEmpty
    private String medicineLinkTemplate;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class PharmacyDTO {
    private Long id;
    private String name;

    public static PharmacyDTO of(Pharmacy pharmacy) {
        return new PharmacyDTO(pharmacy.getId(), pharmacy.getName());
    }
}
