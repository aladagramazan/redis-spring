package com.rem.redis_spring.geo.controller;

import com.rem.redis_spring.geo.dto.Restaurant;
import com.rem.redis_spring.geo.service.RestaurantLocatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("geo")
public class RestaurantController {

    @Autowired
    private RestaurantLocatorService restaurantLocatorService;

    @GetMapping("{zipCode}")
    public Flux<Restaurant> getRestaurants(@PathVariable String zipCode) {
        return this.restaurantLocatorService.getRestaurants(zipCode);
    }
}
