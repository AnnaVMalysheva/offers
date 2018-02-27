package com.example.offers.configuration;

import com.example.offers.dtos.OfferDto;
import com.example.offers.dtos.ParamDto;
import com.example.offers.dtos.PictureDto;
import com.example.offers.entities.Offer;
import com.example.offers.entities.Param;
import com.example.offers.entities.Picture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OfferItemProcessor implements ItemProcessor<OfferDto, Offer> {

    @Override
    public Offer process(final OfferDto offerDto) throws Exception {
        List<Picture> pictureList = new ArrayList<>();
        List<Param> paramList = new ArrayList<>();

        final Offer transformedOffer = Offer.builder()
                .offerId(offerDto.getId())
                .groupId((offerDto.getGroupId() == null) ? offerDto.getId() : offerDto.getGroupId())
                .description(offerDto.getDescription())
                .categoryId(offerDto.getCategoryId())
                .currencyId(offerDto.getCurrencyId())
                .price(offerDto.getPrice())
                .name(offerDto.getName()).build();

        for (PictureDto pictureDto : offerDto.getPictures()) {
            pictureList.add(Picture.builder().url(pictureDto.getPicture()).offer(transformedOffer).build());
        }
        transformedOffer.setPictures(pictureList);
        for (ParamDto paramDto : offerDto.getParams()) {
            paramList.add(Param.builder().name(paramDto.getName()).value(paramDto.getValue()).offer(transformedOffer).build());
        }
        transformedOffer.setParams(paramList);
        log.info("Converting (" + offerDto + ") into (" + transformedOffer + ")");

        return transformedOffer;
    }
}
