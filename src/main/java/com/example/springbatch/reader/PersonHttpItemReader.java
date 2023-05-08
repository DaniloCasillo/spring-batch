package com.example.springbatch.reader;

import com.example.springbatch.model.PersonDTO;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class PersonHttpItemReader implements ItemReader<PersonDTO> {

    private final String apiUrl;
    private final RestTemplate restTemplate;

    private int nextPersonIndex;
    private List<PersonDTO> personData;

    public PersonHttpItemReader(String apiUrl, RestTemplate restTemplate){
        this.apiUrl = apiUrl;
        this.restTemplate = restTemplate;
        nextPersonIndex = 0;
    }

    @Override
    public PersonDTO read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if(personData == null) fetchPersonDataFromAPI();

        PersonDTO nextPerson = null;
        if(nextPersonIndex < personData.size()) {
            nextPerson = personData.get(nextPersonIndex);
            nextPersonIndex++;
        } else {
            nextPersonIndex = 0;
            personData = null;
        }

        return nextPerson;
    }



    private void fetchPersonDataFromAPI(){
        ResponseEntity<PersonDTO[]> response = restTemplate.getForEntity(apiUrl,PersonDTO[].class);
        this.personData = Arrays.asList(response.getBody());

    }
}
