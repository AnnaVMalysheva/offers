package com.example.offers.configuration;

import com.example.offers.entities.Offer;
import com.example.offers.entities.Picture;
import com.example.offers.repositories.PersonRepository;
import com.example.offers.repositories.PictureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
public class OfferItemWriter implements ItemWriter<Offer> {


        @Autowired
        PersonRepository personRepository;

        @Autowired
        PictureRepository pictureRepository;

        @Transactional
        @Override
        public void write(List<? extends Offer> items) throws Exception {
            for (Offer offer : items) {
                Offer existedOffer = personRepository.findByPersonId(offer.getPersonId());
                if (existedOffer != null) {
                    pictureRepository.delete(existedOffer.getPictures());
                    existedOffer.setName(offer.getName());
                    existedOffer.setPictures(offer.getPictures());
                    existedOffer.setCurrencyId(offer.getCurrencyId());
                    existedOffer.setCategoryId(offer.getCategoryId());
                    existedOffer.setDescription(offer.getDescription());
                    existedOffer.setPrice(offer.getPrice());
                    for (Picture picture : offer.getPictures()){
                        picture.setOffer(existedOffer);
                    }
                    personRepository.save(existedOffer);
                } else {
                    personRepository.save(offer);
                }
            }

        }

}
