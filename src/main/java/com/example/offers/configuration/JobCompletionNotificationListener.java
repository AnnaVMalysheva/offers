package com.example.offers.configuration;

import com.example.offers.entities.Offer;
import com.example.offers.entities.Param;
import com.example.offers.entities.Picture;
import com.example.offers.repositories.OfferRepository;
import com.example.offers.repositories.ParamRepository;
import com.example.offers.repositories.PictureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    @Autowired
    OfferRepository offerRepository;

    @Autowired
    PictureRepository pictureRepository;

    @Autowired
    ParamRepository paramRepository;

    @Transactional
    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

            List<Offer> results = offerRepository.findAll();
            for (Offer offer : results) {
                log.info("Found <" + offer.getId() + "> in the database.");
                log.info("Found <" + offer.getName() + "> in the database.");
                log.info("Found <" + offer.getDescription() + "> in the database.");
                log.info("Found <" + offer.getGroupId() + "> in the database.");
                log.info("Found <" + offer.getPictures() + "> in the database.");
            }

            List<Picture> pictures = pictureRepository.findAll();
            for (Picture picture : pictures) {
                log.info("Found <" + picture.getId() + "> in the database.");
                log.info("Found <" + picture.getUrl() + "> in the database.");
                log.info("Found <" + picture.getOffer().getId() + "> in the database.");
            }

            List<Param> params = paramRepository.findAll();
            for (Param param: params) {
                log.info("Found <" + param.getId() + "> in the database.");
                log.info("Found <" + param.getValue() + "> in the database.");
                log.info("Found <" + param.getOffer().getId() + "> in the database.");
            }

        }
    }
}
