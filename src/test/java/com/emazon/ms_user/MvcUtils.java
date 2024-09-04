package com.emazon.ms_user;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

public class MvcUtils {
    private MvcUtils() {}

    public static MultiValueMap<String, String> buildParams(Map<String, String> params) {
        LinkedMultiValueMap<String, String> res = new LinkedMultiValueMap<>();

        params.forEach(res::add);
        return res;
    }
}
