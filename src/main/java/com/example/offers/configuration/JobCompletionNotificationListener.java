package com.example.offers.configuration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.example.offers.dtos.PersonDto;
import com.example.offers.entities.Person;
import com.example.offers.entities.Picture;
import com.example.offers.repositories.PersonRepository;
import com.example.offers.repositories.PictureRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {


    private final JdbcTemplate jdbcTemplate;
    @Autowired
    PersonRepository repository;

    @Autowired
    PictureRepository pictureRepository;

    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

//            List<Person> results = jdbcTemplate.query("SELECT name, email_address,  FROM people", (RowMapper<Person>) (rs, row) -> Person.builder().name(rs.getString(1)).emailAddress(rs.getString(2)).build());
            List<Person> results = repository.findAll();
            for (Person person : results) {
                log.info("Found <" + person.getId() + "> in the database.");
                log.info("Found <" + person.getName() + "> in the database.");
                log.info("Found <" + person.getDescription() + "> in the database.");
                log.info("Found <" + person.getPersonId() + "> in the database.");
                log.info("Found <" + person.getPictures() + "> in the database.");
            }

            List<Picture> pictures = pictureRepository.findAll();
            for (Picture picture : pictures) {
                log.info("Found <" + picture.getId() + "> in the database.");
                log.info("Found <" + picture.getUrl() + "> in the database.");
                log.info("Found <" + picture.getPerson().getId() + "> in the database.");
            }

        }
    }
}
