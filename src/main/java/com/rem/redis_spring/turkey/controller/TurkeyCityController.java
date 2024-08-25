package com.rem.redis_spring.turkey.controller;

import com.rem.redis_spring.turkey.dto.TurkeyCities;
import com.rem.redis_spring.turkey.service.CityLocatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("turkey")
public class TurkeyCityController {

    @Autowired
    private CityLocatorService cityLocatorService;

    @GetMapping("{plaka}")
    public Flux<TurkeyCities> getCities(@PathVariable String plaka) {
        return this.cityLocatorService.getCities(plaka);
    }
}
