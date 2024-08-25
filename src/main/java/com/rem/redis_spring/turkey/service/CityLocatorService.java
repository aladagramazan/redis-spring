package com.rem.redis_spring.turkey.service;

import com.rem.redis_spring.turkey.dto.TurkeyCities;
import com.rem.redis_spring.turkey.dto.TurkeyGeoLocation;
import org.redisson.api.GeoUnit;
import org.redisson.api.RGeoReactive;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.api.geo.GeoSearchArgs;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Service
public class CityLocatorService {
    private final RGeoReactive<TurkeyCities> geo;
    private final RMapReactive<String, TurkeyGeoLocation> map;

    public CityLocatorService(RedissonReactiveClient client) {
        this.geo = client.getGeo("turkeycities", new TypedJsonJacksonCodec(TurkeyCities.class));
        this.map = client.getMap("tr:city", new TypedJsonJacksonCodec(String.class, TurkeyGeoLocation.class));
    }

    public Flux<TurkeyCities> getCities(final String plaka) {
        return this.map.get(plaka)
                .map(gl -> GeoSearchArgs.from(gl.getLongitude(), gl.getLatitude()).radius(400, GeoUnit.KILOMETERS))
                .flatMap(this.geo::search)
                .flatMapIterable(Function.identity());
    }
}
