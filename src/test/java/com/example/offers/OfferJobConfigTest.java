package com.example.offers;

import com.example.offers.configuration.BatchConfiguration;
import com.example.offers.configuration.JobCompletionNotificationListener;
import com.example.offers.configuration.OfferItemProcessor;
import com.example.offers.dtos.OfferDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OfferJobConfigTest {
    @Autowired
    private JobLauncherTestUtils testUtils;

    @Autowired
    private BatchConfiguration config;

    @Autowired
    private JobCompletionNotificationListener listener;

    @Autowired
    private OfferItemProcessor processor;

    public StepExecution getStepExecution() {
        return MetaDataInstanceFactory.createStepExecution();
    }

    @Test
    public void testEntireJob() throws Exception {
        File file = ResourceUtils.getFile(this.getClass().getResource("/test.xml"));
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addString("path", file.getAbsolutePath());
        jobParametersBuilder.addDate("date", new Date());
        final JobExecution result = testUtils.getJobLauncher().run(config.importOfferJob(listener), jobParametersBuilder.toJobParameters());
        assertNotNull(result);
        Assert.assertEquals(BatchStatus.COMPLETED, result.getStatus());
    }

    @Test
    public void testSpecificStep() throws FileNotFoundException {
        File file = ResourceUtils.getFile(this.getClass().getResource("/test.xml"));
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addString("path", file.getAbsolutePath());
        jobParametersBuilder.addDate("date", new Date());
        Assert.assertEquals(BatchStatus.COMPLETED, testUtils.launchStep("step1", jobParametersBuilder.toJobParameters()).getStatus());
    }


    @Test
    public void filter() throws Exception {
        final OfferDto offerDto = new OfferDto();
        offerDto.setName("name");
        offerDto.setId("12345");
//        offerDto.setPictures(Collections.EMPTY_LIST);
        assertEquals(offerDto.getId(), processor.process(offerDto).getPersonId());
    }
}
