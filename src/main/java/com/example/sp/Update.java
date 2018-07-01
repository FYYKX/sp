package com.example.sp;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class Update {
    @Email
    private String requestor;
    @Email
    private String target;
}
