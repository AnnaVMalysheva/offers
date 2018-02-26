package com.example.offers.configuration;

import com.example.offers.dtos.PersonDto;
import com.example.offers.entities.Person;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;
    private static final String OVERRIDDEN_BY_EXPRESSION = null;
    // tag::readerwriterprocessor[]
    @Bean
    @StepScope
    public ItemStreamReader<PersonDto> reader(@Value("#{jobParameters['path']}") String path) {
        StaxEventItemReader<PersonDto> xmlFileReader = new StaxEventItemReader<>();

        xmlFileReader.setResource(new FileSystemResource(path));
        xmlFileReader.setFragmentRootElementName("offer");

        Jaxb2Marshaller studentMarshaller = new Jaxb2Marshaller();
        studentMarshaller.setClassesToBeBound(PersonDto.class);
        xmlFileReader.setUnmarshaller(studentMarshaller);

        return xmlFileReader;
    }


    @Bean
    public PersonItemWriter writer() {
//        JdbcBatchItemWriter<PersonDto> writer = new JdbcBatchItemWriter<PersonDto>();
//        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<PersonDto>());
//        writer.setSql("INSERT INTO people (name, emailAddress) VALUES (:name, :emailAddress)");
//        writer.setDataSource(dataSource);
//        return writer;
        return new PersonItemWriter();
    }
    // end::readerwriterprocessor[]

    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

    // tag::jobstep[]
    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener).start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<PersonDto, Person> chunk(10)
                .reader(reader(OVERRIDDEN_BY_EXPRESSION))
                .processor(processor())
                .writer(writer())
                .build();
    }
    // end::jobstep[]
}
