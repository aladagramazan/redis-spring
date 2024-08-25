package com.rem.redis_spring.city.service;

import com.rem.redis_spring.city.client.CityClient;
import com.rem.redis_spring.city.dto.City;
import org.redisson.api.RMapCacheReactive;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityClient cityClient;

    private RMapReactive<String, City> cityMap;  // for ttl user RMapCacheReactive

    // private RMapCacheReactive<String, City> cityMap;

    // not ttl use RMapReactive
    public CityService(RedissonReactiveClient client) {
        this.cityMap = client.getMap("city", new TypedJsonJacksonCodec(String.class, City.class));

        // ttl -->   this.cityMap = client.getMapCache("city", new TypedJsonJacksonCodec(String.class, City.class));

        // not ttl use getMap
    }

    // get from cache
    // if not found, get from external service and cache it

    public Mono<City> getCity(final String zipCode) {
        return this.cityMap.get(zipCode);  // get from cache
             //   .onErrorResume(ex -> this.cityClient.getCity(zipCode)); // get from external service
        //  .switchIfEmpty(this.cityClient.getCity(zipCode)) // get from external service
        //  .flatMap(city -> this.cityMap.fastPut(zipCode, city, 10, TimeUnit.SECONDS).thenReturn(city)); // cache it

        //  .flatMap(city -> this.cityMap.fastPut(zipCode, city).thenReturn(city)); not ttl
    }

 /*   @Scheduled(fixedRate = 10_000)
    public void updateCity() {   // first stored cache and update cache
        System.out.println("updateCity");
        this.cityClient.getAllCity()
                .collectList()
                .map(cities -> cities.stream().collect(Collectors.toMap(City::getZip, Function.identity())))
                .flatMap(this.cityMap::putAll)
                .subscribe();
    } */
}
