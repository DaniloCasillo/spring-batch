package com.example.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@RequiredArgsConstructor
@EnableBatchProcessing
@EnableScheduling
public class SpringBatchApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringBatchApplication.class, args);
	}

}