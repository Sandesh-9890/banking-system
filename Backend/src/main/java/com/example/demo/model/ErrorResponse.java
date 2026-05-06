package com.example.demo.model;

import java.time.LocalDateTime;

public class ErrorResponse {

    private int status;
    private String message;
    private LocalDateTime time;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.time = LocalDateTime.now();
    }

    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public LocalDateTime getTime() { return time; }
}