package com.rem.redis_spring.city.dto;

import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class City {
    private String zip;
    private String city;
    private String stateName;
    private int temperature;
}
