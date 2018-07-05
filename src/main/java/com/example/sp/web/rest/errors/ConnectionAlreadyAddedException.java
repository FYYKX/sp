package com.example.sp.web.rest.errors;

public class ConnectionAlreadyAddedException extends SPException {
    public ConnectionAlreadyAddedException() {
        super("Connection already added");
    }
}
