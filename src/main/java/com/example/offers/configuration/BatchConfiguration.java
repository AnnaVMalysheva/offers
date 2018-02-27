package com.example.offers.configuration;

import com.example.offers.dtos.OfferDto;
import com.example.offers.entities.Offer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {


    private JobBuilderFactory jobBuilderFactory;

    private StepBuilderFactory stepBuilderFactory;


    private static final String OVERRIDDEN_BY_EXPRESSION = null;

    public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }


    @Bean
    @StepScope
    public ItemStreamReader<OfferDto> reader(@Value("#{jobParameters['path']}") String path) {
        StaxEventItemReader<OfferDto> xmlFileReader = new StaxEventItemReader<>();
        xmlFileReader.setResource(new FileSystemResource(path));
        xmlFileReader.setFragmentRootElementName("offer");
        Jaxb2Marshaller studentMarshaller = new Jaxb2Marshaller();
        studentMarshaller.setClassesToBeBound(OfferDto.class);
        xmlFileReader.setUnmarshaller(studentMarshaller);

        return xmlFileReader;
    }


    @Bean
    public OfferItemWriter writer() {
        return new OfferItemWriter();
    }

    @Bean
    public OfferItemProcessor processor() {
        return new OfferItemProcessor();
    }

    @Bean
    public Job importOfferJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("importOfferJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<OfferDto, Offer> chunk(10)
                .reader(reader(OVERRIDDEN_BY_EXPRESSION))
                .processor(processor())
                .writer(writer())
                .build();
    }
}
