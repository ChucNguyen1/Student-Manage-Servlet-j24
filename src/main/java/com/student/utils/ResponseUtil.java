package com.student.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for formatting HTTP responses
 */
public class ResponseUtil {
    
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    
    /**
     * Gửi JSON response
     */
    public static void sendJsonResponse(HttpServletResponse response, int statusCode, Object data) 
            throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(data));
        out.flush();
    }
    
    /**
     * Gửi success response
     */
    public static void sendSuccess(HttpServletResponse response, Object data) throws IOException {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("success", true);
        responseData.put("data", data);
        sendJsonResponse(response, HttpServletResponse.SC_OK, responseData);
    }
    
    /**
     * Gửi success response với message
     */
    public static void sendSuccess(HttpServletResponse response, String message, Object data) 
            throws IOException {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("success", true);
        responseData.put("message", message);
        responseData.put("data", data);
        sendJsonResponse(response, HttpServletResponse.SC_OK, responseData);
    }
    
    /**
     * Gửi error response
     */
    public static void sendError(HttpServletResponse response, int statusCode, String message) 
            throws IOException {
        Map<String, Object> errorData = new HashMap<>();
        errorData.put("success", false);
        errorData.put("error", message);
        sendJsonResponse(response, statusCode, errorData);
    }
    
    /**
     * Gửi unauthorized error (401)
     */
    public static void sendUnauthorized(HttpServletResponse response, String message) 
            throws IOException {
        sendError(response, HttpServletResponse.SC_UNAUTHORIZED, message);
    }
    
    /**
     * Gửi forbidden error (403)
     */
    public static void sendForbidden(HttpServletResponse response, String message) 
            throws IOException {
        sendError(response, HttpServletResponse.SC_FORBIDDEN, message);
    }
    
    /**
     * Gửi bad request error (400)
     */
    public static void sendBadRequest(HttpServletResponse response, String message) 
            throws IOException {
        sendError(response, HttpServletResponse.SC_BAD_REQUEST, message);
    }
    
    /**
     * Gửi internal server error (500)
     */
    public static void sendInternalError(HttpServletResponse response, String message) 
            throws IOException {
        sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
    }
    
    /**
     * Convert object to JSON string
     */
    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }
    
    /**
     * Parse JSON string to object
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
}
