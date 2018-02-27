package com.example.offers.repositories;

import com.example.offers.entities.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Offer, Integer> {
    Offer findByPersonId(String personId);
}
