package com.example.offers.configuration;

import com.example.offers.entities.OfferVariation;
import com.example.offers.entities.Param;
import com.example.offers.entities.Picture;
import com.example.offers.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class OfferVariationItemWriter implements ItemWriter<OfferVariation> {

        @Autowired
        OfferRepository offerRepository;

        @Autowired
        OfferVariationRepository offerVariationRepository;

        @Autowired
        PictureRepository pictureRepository;

        @Autowired
        ParamRepository paramRepository;



        @Override
        public void write(List<? extends OfferVariation> items) throws Exception {
            for (OfferVariation offer : items) {

                OfferVariation existedOffer = offerVariationRepository.findByOfferId(offer.getOfferId());
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
                    offerVariationRepository.save(existedOffer);
                } else {
                    offerVariationRepository.save(offer);
                }
            }

        }

}
