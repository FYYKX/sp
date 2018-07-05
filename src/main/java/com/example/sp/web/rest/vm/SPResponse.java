package com.example.sp.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SPResponse {
    private boolean success;
    private String message;

    public SPResponse(boolean success) {
        this.success = success;
    }

    public SPResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
