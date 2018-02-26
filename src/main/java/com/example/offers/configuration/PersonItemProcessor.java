package com.example.offers.configuration;

import com.example.offers.dtos.PersonDto;
import com.example.offers.dtos.PictureDto;
import com.example.offers.entities.Person;
import com.example.offers.entities.Picture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PersonItemProcessor implements ItemProcessor<PersonDto, Person> {

@Override
public Person process(final PersonDto person) throws Exception {
    List<Picture> pictureList = new ArrayList<>();

    final Person transformedPerson = Person.builder()
            .personId(person.getId())
            .description(person.getDescription())
            .categoryId(person.getCategoryId())
            .currencyId(person.getCurrencyId())
            .price(person.getPrice())
            .name(person.getName()).build();

    for (PictureDto pictureDto: person.getPictures()){
            pictureList.add(Picture.builder().url(pictureDto.getPicture()).person(transformedPerson).build());
        }
    transformedPerson.setPictures(pictureList);
        log.info("Converting (" + person + ") into (" + transformedPerson + ")");

        return transformedPerson;
        }
}
