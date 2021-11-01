package com.eleks.academy.pharmagator.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pharmacies")
public class Pharmacy {
    @Id
    // FIXME додати всі ентетям цю анотацію
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    // GenerationType.IDENTITY означає що ми віддаємо генерацю цього поля ІД передаємо на рівень бд
    private Long id;
    @Column(nullable = false)
    private String name;
    private String medicineLinkTemplate;
}
