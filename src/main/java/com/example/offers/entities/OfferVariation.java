package com.example.offers.entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("GROUP")
public class OfferVariation extends Offer{

    @Column(name="group_id")
    private String groupId;
}
