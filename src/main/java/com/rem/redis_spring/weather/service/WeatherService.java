package com.rem.redis_spring.weather.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

@Service
public class WeatherService {

    @Autowired
    private ExternalServiceClient client;

    @Cacheable(value = "weather")
    public int getInfo(int zip) {
        return 0;
    }

   /* @Scheduled(fixedRate = 10_000)
    public void update() {
        System.out.println("updating weather info");
        IntStream.range(1, 5)
                .forEach(i -> this.client.getWeatherInfo(i));
    } */
}
