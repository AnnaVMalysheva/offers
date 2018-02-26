package com.example.offers.configuration;

import com.example.offers.entities.Person;
import com.example.offers.entities.Picture;
import com.example.offers.repositories.PictureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.offers.repositories.PersonRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
public class PersonItemWriter implements ItemWriter<Person> {


        @Autowired
        PersonRepository personRepository;

        @Autowired
        PictureRepository pictureRepository;

        @Transactional
        @Override
        public void write(List<? extends Person> items) throws Exception {
            for (Person person : items) {
                Person existedPerson = personRepository.findByPersonId(person.getPersonId());
                if (existedPerson != null) {
                    pictureRepository.delete(existedPerson.getPictures());
                    existedPerson.setName(person.getName());
                    existedPerson.setPictures(person.getPictures());
                    existedPerson.setCurrencyId(person.getCurrencyId());
                    existedPerson.setCategoryId(person.getCategoryId());
                    existedPerson.setDescription(person.getDescription());
                    existedPerson.setPrice(person.getPrice());
                    for (Picture picture : person.getPictures()){
                        picture.setPerson(existedPerson);
                    }
                    personRepository.save(existedPerson);
                } else {
                    personRepository.save(person);
                }
            }

        }

}
