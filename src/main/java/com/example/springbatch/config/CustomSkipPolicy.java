package com.example.springbatch.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

@Slf4j
public class CustomSkipPolicy implements SkipPolicy {



    @Override
    public boolean shouldSkip(Throwable throwable, int i) throws SkipLimitExceededException {
      log.error("Errore durante l'esecuzione del job {}",throwable.getMessage());
      return true;
    }
}
