package com.example.sp.web.rest.errors;

public class AlreadyUpdateException extends SPException {
    public AlreadyUpdateException(String type) {
        super("Already " + type);
    }
}
