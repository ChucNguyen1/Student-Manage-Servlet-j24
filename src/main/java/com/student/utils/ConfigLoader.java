package com.student.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class để load config từ file properties một cách an toàn
 * Best practice: Không hardcode sensitive data trong code
 */
public class ConfigLoader {
    
    private static final String CONFIG_FILE = "config.properties";
    private static Properties properties;
    
    static {
        loadProperties();
    }
    
    /**
     * Load config file từ resources
     */
    private static void loadProperties() {
        properties = new Properties();
        
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                System.err.println("⚠️  Không tìm thấy file " + CONFIG_FILE);
                System.err.println("💡 Vui lòng copy config.properties.example thành config.properties");
                return;
            }
            
            properties.load(input);
            System.out.println("✅ Đã load config từ " + CONFIG_FILE);
            
        } catch (IOException e) {
            System.err.println("❌ Lỗi khi đọc file config: " + e.getMessage());
        }
    }
    
    /**
     * Lấy giá trị config theo key
     * 
     * @param key Key trong file properties
     * @return Giá trị hoặc null nếu không tìm thấy
     */
    public static String get(String key) {
        return properties.getProperty(key);
    }
    
    /**
     * Lấy giá trị config với default value
     * 
     * @param key Key trong file properties
     * @param defaultValue Giá trị mặc định nếu không tìm thấy
     * @return Giá trị hoặc defaultValue
     */
    public static String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    /**
     * Lấy giá trị boolean
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) return defaultValue;
        return Boolean.parseBoolean(value);
    }
    
    /**
     * Lấy giá trị integer
     */
    public static int getInt(String key, int defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) return defaultValue;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    /**
     * Lấy giá trị double
     */
    public static double getDouble(String key, double defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) return defaultValue;
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    /**
     * Kiểm tra xem có config hay không
     */
    public static boolean hasConfig() {
        return properties != null && !properties.isEmpty();
    }
    
    /**
     * Reload config (useful khi update config mà không muốn restart server)
     */
    public static void reload() {
        loadProperties();
    }
}
