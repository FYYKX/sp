package com.example.sp;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class Message {
    @Email
    private String sender;
    @NotBlank
    private String text;
}
