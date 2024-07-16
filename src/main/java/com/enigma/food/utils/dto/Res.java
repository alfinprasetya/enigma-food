package com.enigma.food.utils.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Res {

    public static <T> ResponseEntity<?> renderJson(
            T data, HttpStatus status, String message
    ) {
        WebResponse<T> response = WebResponse.<T>builder()
                .status(status.getReasonPhrase())
                .message(message)
                .data(data)
                .build();
        return ResponseEntity.status(status).body(response);
    }

}
