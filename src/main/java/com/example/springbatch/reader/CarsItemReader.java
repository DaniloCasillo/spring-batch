package com.example.springbatch.reader;

import com.example.springbatch.config.CarBatchConfiguration;
import com.example.springbatch.model.CarDTO;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class CarsItemReader implements ItemReader<CarDTO> {

    private final String apiUrl;
    private final RestTemplate restTemplate;

    private int nextPersonIndex;
    private List<CarDTO> personData;

    public CarsItemReader(String apiUrl, RestTemplate restTemplate){
        this.apiUrl = apiUrl;
        this.restTemplate = restTemplate;
        nextPersonIndex = 0;
    }

    @Override
    public CarDTO read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if(personData == null) fetchPersonDataFromAPI();

        CarDTO nextPerson = null;
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
        ResponseEntity<CarDTO[]> response = restTemplate.getForEntity(apiUrl,CarDTO[].class);
        this.personData = Arrays.asList(response.getBody());

    }
}
