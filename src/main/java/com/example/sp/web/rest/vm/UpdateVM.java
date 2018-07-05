package com.example.sp.web.rest.vm;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UpdateVM {
    @Email
    @NotBlank
    private String requestor;
    @Email
    @NotBlank
    private String target;
}
