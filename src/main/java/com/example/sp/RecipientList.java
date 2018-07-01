package com.example.sp;

import lombok.Data;

import java.util.List;

@Data
public class RecipientList {
    private boolean success;
    private List<String> recipients;

    public RecipientList(boolean success, List<String> recipients) {
        this.success = success;
        this.recipients = recipients;
    }
}
