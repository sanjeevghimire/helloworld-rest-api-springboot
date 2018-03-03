package com.comcast.helloworld.util;

import org.springframework.http.HttpHeaders;

public final class HeaderUtil {


    private HeaderUtil() {
    }

    public static HttpHeaders createAlert(String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-helloWorld-alert", message);
        headers.add("X-helloWorld-params", param);
        return headers;
    }

}
