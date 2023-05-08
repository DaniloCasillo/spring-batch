package com.example.springbatch.config;

import com.example.springbatch.model.CarDTO;
import com.example.springbatch.reader.CarsItemReader;
import com.example.springbatch.writer.PersonItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Configuration
public class CarBatchConfiguration {

    private final StepBuilderFactory stepBuilderFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private final KafkaTemplate<String,CarDTO> kafkaTemplate;


    @Value("${spring.batch.input.car}")
    private String inputUrl;

    @Value("${spring.kafka.topics.car}")
    private String outputTopic;

    @Autowired
    private JobLauncher jobLauncher;

    public CarBatchConfiguration(StepBuilderFactory stepBuilderFactory, JobBuilderFactory jobBuilderFactory, KafkaTemplate<String, CarDTO> kafkaTemplate) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.jobBuilderFactory = jobBuilderFactory;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Bean(name = "reader-car")
    public ItemReader<CarDTO> reader(){
        return new CarsItemReader(inputUrl,new RestTemplate());
    }


    @Bean(name = "writer-car")
    PersonItemWriter<CarDTO> writer(){
        return new PersonItemWriter(outputTopic,kafkaTemplate);
    }

    @Bean(name = "step-car")
    Step step(){
        return stepBuilderFactory.get("step-car")
                .<CarDTO,CarDTO>chunk(10)
                .reader(reader())
                .writer(writer())
                .build();
    }

    @Bean(name = "job-car")
    Job job() {
        return jobBuilderFactory.get("job-car")
                .start(step())
                .build();
    }

    public void launchJob() throws Exception {
        jobLauncher.run(job(),new JobParametersBuilder().addDate("date",new Date()).toJobParameters());
    }
}
