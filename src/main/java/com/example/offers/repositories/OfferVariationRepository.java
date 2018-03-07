package com.example.offers.repositories;

import com.example.offers.entities.OfferVariation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferVariationRepository extends JpaRepository<OfferVariation, Integer> {
    OfferVariation findByGroupId(String groupId);
    OfferVariation findByOfferId(String offerId);
}
