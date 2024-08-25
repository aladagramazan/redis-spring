package com.rem.redis_spring.turkey.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TurkeyCities {
    private String plaka;
    private String il_adi;
    private double lat;
    private double lon;
    private double northeast_lat;
    private double northeast_lon;
    private double southwest_lat;
    private double southwest_lon;
}
