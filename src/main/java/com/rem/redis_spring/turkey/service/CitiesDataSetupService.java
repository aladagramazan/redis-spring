package com.rem.redis_spring.turkey.service;

import com.rem.redis_spring.geo.dto.GeoLocation;
import com.rem.redis_spring.geo.dto.Restaurant;
import com.rem.redis_spring.geo.util.RestaurantUtil;
import com.rem.redis_spring.turkey.dto.TurkeyCities;
import com.rem.redis_spring.turkey.dto.TurkeyGeoLocation;
import com.rem.redis_spring.turkey.util.TurkeyCitiesUtil;
import org.redisson.api.RGeoReactive;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class CitiesDataSetupService implements CommandLineRunner {

    private RGeoReactive<TurkeyCities> geo;
    private RMapReactive<String, TurkeyGeoLocation> map;

    @Autowired
    private RedissonReactiveClient client;

    @Override
    public void run(String... args) throws Exception {
        this.geo = this.client.getGeo("turkeycities", new TypedJsonJacksonCodec(TurkeyCities.class));
        this.map = this.client.getMap("tr:city", new TypedJsonJacksonCodec(String.class, TurkeyGeoLocation.class));

        Flux.fromIterable(TurkeyCitiesUtil.getCities())
                .flatMap(r -> this.geo.add(r.getLon(), r.getLat(), r).thenReturn(r))
                .flatMap(r -> this.map.fastPut(r.getPlaka(), TurkeyGeoLocation.of(r.getLon(), r.getLat())))
                .doFinally(s -> System.out.println("city added " + s))
                .subscribe();
    }
}
