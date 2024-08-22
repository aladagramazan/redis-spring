package com.rem.redis_spring.city.client;

import com.rem.redis_spring.city.dto.City;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CityClient {

    private final WebClient client;

    public CityClient(@Value("${city.service.url}") String url) {
        this.client = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<City> getCity(final String zipCode) {
        return this.client.get()
                .uri("{zipCode}", zipCode)
                .retrieve()
                .bodyToMono(City.class);
    }

    public Flux<City> getAllCity() {
        return this.client.get()
                .retrieve()
                .bodyToFlux(City.class);
    }
}
