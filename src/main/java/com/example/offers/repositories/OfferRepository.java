package com.example.offers.repositories;

import com.example.offers.entities.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer> {
    Offer findByOfferId(String offerId);
}
