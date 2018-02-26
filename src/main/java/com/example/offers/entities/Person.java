package com.example.offers.entities;

import lombok.*;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

@Getter
@Setter

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="people")
public class Person {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;

    @Column(name="person_id")
    private String personId;

    @Column(name="currency_id")
    private String currencyId;

    @Lob
    private String description;

    private Double price;

    @Column(name="category_id")
    private Integer categoryId;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "person")
    private List<Picture> pictures;
}