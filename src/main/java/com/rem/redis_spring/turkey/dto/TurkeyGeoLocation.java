package com.rem.redis_spring.turkey.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class TurkeyGeoLocation {
    private double longitude;
    private double latitude;
}
