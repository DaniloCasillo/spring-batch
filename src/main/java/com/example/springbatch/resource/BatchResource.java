package com.example.springbatch.resource;

import com.example.springbatch.config.CarBatchConfiguration;
import com.example.springbatch.config.PersonBatchConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchResource {
    private static final String BASE_URL = "/api/v1/batch";

    @Autowired
    private CarBatchConfiguration carBatchConfiguration;

    @Autowired
    private PersonBatchConfiguration personBatchConfiguration;

    @GetMapping(value = BASE_URL+"/car")
    public void startCarBatch() throws Exception {
        carBatchConfiguration.launchJob();
    }

    @GetMapping(value = BASE_URL+"/person")
    public void startPersonBatch() throws Exception {
        personBatchConfiguration.launchJob();
    }
}
