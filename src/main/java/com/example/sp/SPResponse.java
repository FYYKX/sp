package com.example.sp;

import lombok.Data;

@Data
public class SPResponse {
    private boolean success;

    public SPResponse(boolean success) {
        this.success = success;
    }
}
