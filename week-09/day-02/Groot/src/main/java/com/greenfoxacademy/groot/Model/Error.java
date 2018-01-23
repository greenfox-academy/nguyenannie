package com.greenfoxacademy.groot.Model;

import org.springframework.stereotype.Component;

@Component
public class Error extends RestResponse{
    private String error;

    public Error() {

    }

    public Error(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
