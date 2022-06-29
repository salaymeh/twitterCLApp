package ca.jrvs.apps.twitter.util;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import ca.jrvs.apps.twitter.example.dto.Company;

public class JsonParser {
    
    public static String toJson(Object object, boolean prettyJson, boolean includeNullValues) throws JsonProcessingException{

        ObjectMapper m = new ObjectMapper();
        if(!includeNullValues){
            m.setSerializationInclusion(Include.NON_NULL);
        }
        if(prettyJson){
            m.enable(SerializationFeature.INDENT_OUTPUT);

        }
        return m.writeValueAsString(object);
    }

    public static <T> T toObjectFromJson(String json, Class clazz) throws IOException{
        ObjectMapper m = new ObjectMapper();
        return (T) m.readValue(json, clazz);
    }

    
}
