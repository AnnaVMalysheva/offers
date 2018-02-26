package com.example.offers.controllers;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class JobLauncherController {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job importUserJob;

    @GetMapping("/jobLauncher")
    public ResponseEntity<Object> handle(@RequestParam("fileurl") String fileUrl) throws Exception{
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addString("path", fileUrl);
        jobParametersBuilder.addDate("date", new Date());
        jobLauncher.run(importUserJob, jobParametersBuilder.toJobParameters());
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
