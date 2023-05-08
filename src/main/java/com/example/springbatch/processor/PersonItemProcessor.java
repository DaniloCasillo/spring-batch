package com.example.springbatch.processor;

import com.example.springbatch.model.PersonDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;


@Slf4j
public class PersonItemProcessor implements ItemProcessor<PersonDTO,PersonDTO> {


    @Override
    public PersonDTO process(PersonDTO personDTO) throws Exception {
        
        return personDTO;
    }
}
