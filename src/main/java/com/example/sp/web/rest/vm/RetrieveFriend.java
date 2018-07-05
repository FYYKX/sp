package com.example.sp.web.rest.vm;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class RetrieveFriend {
    @NotBlank
    @Email
    private String email;
}
