package com.bl.ai.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Standard API response wrapper for frontend/backend agreement.
 * Fields: code, data, error, message
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private int code;
    private T data;
    /**
     * error is a simple string message describing the error (per requirement)
     */
    private String error;
    private String message;

    public ApiResponse() {}

    public ApiResponse(int code, T data, String error, String message) {
        this.code = code;
        this.data = data;
        this.error = error;
        this.message = message;
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(0, data, null, "ok");
    }

    public static <T> ApiResponse<T> succeed(T data, String message) {
        return new ApiResponse<>(0, data, null, message == null ? "ok" : message);
    }

    public static <T> ApiResponse<T> of(int code, T data, String message) {
        return new ApiResponse<>(code, data, null, message);
    }

    public static <T> ApiResponse<T> error(int code, String message, String error) {
        return new ApiResponse<>(code, null, error, message);
    }

    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
