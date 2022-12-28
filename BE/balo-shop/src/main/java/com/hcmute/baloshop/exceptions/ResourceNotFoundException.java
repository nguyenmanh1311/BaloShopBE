package com.hcmute.baloshop.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{

    private String resourceName;
    private String fieldName;
    private Object fieldValue;
    public ResourceNotFoundException(){
        super();
    }

    public ResourceNotFoundException(String message){
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause){
        super(message, cause);
    }

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public ResourceNotFoundException(String originResouceName, String firstFieldName, Object firstFieldValue, String secondFieldName, Object secondFieldValue) {
        super(String.format("%s not found with %s : '%s' and %s : '%s'",
                originResouceName, firstFieldName, firstFieldValue, secondFieldName, secondFieldValue));
        this.resourceName = originResouceName;
        this.fieldName = String.format("[{%s}, {%s}]", firstFieldName, secondFieldName);
        this.fieldValue = String.format("[{%s}, {%s}]", firstFieldValue, secondFieldValue);;
    }
}