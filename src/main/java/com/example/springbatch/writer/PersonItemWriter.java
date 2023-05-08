package com.example.springbatch.writer;

import com.example.springbatch.model.PersonDTO;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

@AllArgsConstructor
public class PersonItemWriter<T> implements ItemWriter<T> {

    private final String topic;

    @Autowired
    private KafkaTemplate<String,T> kafkaTemplate;

    @Override
    public void write(List<? extends T> list) throws Exception {
        list.forEach(l -> {
            System.out.println("Writing on kafka topic : " + topic);
            kafkaTemplate.send(topic,l);
        });
    }
}
