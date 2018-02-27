package com.example.offers.configuration;

import com.example.offers.entities.Offer;
import com.example.offers.entities.Param;
import com.example.offers.entities.Picture;
import com.example.offers.repositories.OfferRepository;
import com.example.offers.repositories.ParamRepository;
import com.example.offers.repositories.PictureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
public class OfferItemWriter implements ItemWriter<Offer> {


        @Autowired
        OfferRepository offerRepository;

        @Autowired
        PictureRepository pictureRepository;

        @Autowired
        ParamRepository paramRepository;


        @Transactional
        @Override
        public void write(List<? extends Offer> items) throws Exception {
            for (Offer offer : items) {
                Offer existedOffer = offerRepository.findByOfferId(offer.getOfferId());
                if (existedOffer != null) {
                    pictureRepository.delete(existedOffer.getPictures());
                    paramRepository.delete(existedOffer.getParams());
                    existedOffer.setName(offer.getName());
                    existedOffer.setPictures(offer.getPictures());
                    existedOffer.setParams(offer.getParams());
                    existedOffer.setCurrencyId(offer.getCurrencyId());
                    existedOffer.setCategoryId(offer.getCategoryId());
                    existedOffer.setDescription(offer.getDescription());
                    existedOffer.setPrice(offer.getPrice());
                    for (Picture picture : offer.getPictures()){
                        picture.setOffer(existedOffer);
                    }
                    for (Param param : offer.getParams()){
                        param.setOffer(existedOffer);
                    }
                    offerRepository.save(existedOffer);
                } else {
                    offerRepository.save(offer);
                }
            }

        }

}
