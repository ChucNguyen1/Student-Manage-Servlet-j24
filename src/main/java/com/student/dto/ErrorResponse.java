package com.student.dto;

/**
 * DTO cho error response
 */
public class ErrorResponse {
    private boolean success;
    private String error;
    private String message;
    private int statusCode;
    
    public ErrorResponse() {
        this.success = false;
    }
    
    public ErrorResponse(String error) {
        this.success = false;
        this.error = error;
    }
    
    public ErrorResponse(String error, String message) {
        this.success = false;
        this.error = error;
        this.message = message;
    }
    
    public ErrorResponse(String error, String message, int statusCode) {
        this.success = false;
        this.error = error;
        this.message = message;
        this.statusCode = statusCode;
    }
    
    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public String getError() {
        return error;
    }
    
    public void setError(String error) {
        this.error = error;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public int getStatusCode() {
        return statusCode;
    }
    
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
