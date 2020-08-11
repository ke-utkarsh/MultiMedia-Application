package com.example.projecttwo;

import java.util.List;

public class LogInResponse {
    private String token;
    private List<String> nonFieldErrors=null;

    public List<String> getNonFieldErrors() {
        return nonFieldErrors;
    }

    public void setNonFieldErrors(List<String> nonFieldErrors) {
        this.nonFieldErrors = nonFieldErrors;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
