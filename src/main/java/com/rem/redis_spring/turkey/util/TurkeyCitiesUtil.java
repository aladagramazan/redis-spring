package com.rem.redis_spring.turkey.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rem.redis_spring.geo.util.RestaurantUtil;
import com.rem.redis_spring.turkey.dto.TurkeyCities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class TurkeyCitiesUtil {

    public static List<TurkeyCities> getCities() {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream resourceAsStream = RestaurantUtil.class.getClassLoader().getResourceAsStream("cities.json");
        try {
            return objectMapper.readValue(resourceAsStream, new TypeReference<List<TurkeyCities>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
