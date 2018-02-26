package com.example.offers.repositories;

import com.example.offers.dtos.PersonDto;
import com.example.offers.entities.Person;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Person findByPersonId(String personId);
}
