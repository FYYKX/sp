package com.example.sp;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class RetrieveFriend {
    @Email
    private String email;
}
