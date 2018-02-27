package com.example.offers.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="offer")
public class Offer {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;

    @Column(name="group_id")
    private String groupId;

    @Column(name="offer_id")
    private String offerId;

    @Column(name="currency_id")
    private String currencyId;

    @Lob
    private String description;

    private Double price;

    @Column(name="category_id")
    private Integer categoryId;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "offer")
    private List<Picture> pictures = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "offer")
    private List<Param> params = new ArrayList<>();
}
