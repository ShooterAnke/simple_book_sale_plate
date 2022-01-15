package net.anke.books_sales_platform.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    private static final ObjectMapper mapper =new ObjectMapper();
    public static String objectToJson(Object obj){
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T jsonToObject(String json,Class<T> tClass){
        try {
            return mapper.readValue(json,tClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
