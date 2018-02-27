package com.example.offers.configuration;

import com.example.offers.dtos.OfferDto;
import com.example.offers.dtos.PictureDto;
import com.example.offers.entities.Offer;
import com.example.offers.entities.Picture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OfferItemProcessor implements ItemProcessor<OfferDto, Offer> {

@Override
public Offer process(final OfferDto person) throws Exception {
    List<Picture> pictureList = new ArrayList<>();

    final Offer transformedOffer = Offer.builder()
            .personId(person.getId())
            .description(person.getDescription())
            .categoryId(person.getCategoryId())
            .currencyId(person.getCurrencyId())
            .price(person.getPrice())
            .name(person.getName()).build();

    for (PictureDto pictureDto: person.getPictures()){
            pictureList.add(Picture.builder().url(pictureDto.getPicture()).offer(transformedOffer).build());
        }
    transformedOffer.setPictures(pictureList);
        log.info("Converting (" + person + ") into (" + transformedOffer + ")");

        return transformedOffer;
        }
}
