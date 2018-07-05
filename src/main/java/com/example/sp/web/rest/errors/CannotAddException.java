package com.example.sp.web.rest.errors;

public class CannotAddException extends SPException {
    public CannotAddException() {
        super("Can not add connection");
    }
}
