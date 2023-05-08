package com.example.springbatch.config;

import com.example.springbatch.model.PersonDTO;
import com.example.springbatch.processor.PersonItemProcessor;
import com.example.springbatch.reader.PersonHttpItemReader;
import com.example.springbatch.writer.PersonItemWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
@Slf4j
public class PersonBatchConfiguration{


    private final StepBuilderFactory stepBuilderFactory;
    private final JobBuilderFactory jobBuilderFactory;

    private final KafkaTemplate<String,PersonDTO> kafkaTemplate;

    @Value("${spring.batch.input.person}")
    private String inputUrl;

    @Value("${spring.kafka.topics.person}")
    private String outputTopic;

    private AtomicBoolean enabled = new AtomicBoolean(true);
    private AtomicInteger batchRunCounter = new AtomicInteger(0);

    @Autowired
    private JobLauncher jobLauncher;

    public PersonBatchConfiguration(StepBuilderFactory stepBuilderFactory, JobBuilderFactory jobBuilderFactory, KafkaTemplate<String, PersonDTO> kafkaTemplate) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.jobBuilderFactory = jobBuilderFactory;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Bean(name = "reader-person")
    public ItemReader<PersonDTO> reader(){
        return new PersonHttpItemReader(inputUrl,new RestTemplate());
    }

    @Bean(name = "processor-person")
    public ItemProcessor<PersonDTO, PersonDTO> processor(){
        return new PersonItemProcessor();
    }

    @Bean(name = "writer-person")
    PersonItemWriter<PersonDTO> writer(){
        return new PersonItemWriter(outputTopic,kafkaTemplate);
    }

    @Bean(name = "step-person")
    Step step(){
        return stepBuilderFactory.get("step-person")
                .<PersonDTO,PersonDTO>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .faultTolerant().skipPolicy(new CustomSkipPolicy())
                .build();
    }

    @Bean(name = "job-person")
    Job job() {
        return jobBuilderFactory.get("job-person")
                .start(step())
                .build();
    }

    //@Scheduled(cron = "${spring.batch.cron_expression}")
    public void launchJob() throws Exception {
        jobLauncher.run(job(),new JobParametersBuilder().addDate("date",new Date()).toJobParameters());
    }






}
