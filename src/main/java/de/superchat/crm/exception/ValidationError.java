package de.superchat.crm.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ValidationError {
    private final String message;
    private final String[] details;
    /**
     * This constructor is used when we need to set the field and multiple validation messages for the field
     * @param message the field that validation messages is related to
     * @param details the validation messages
     */
    public ValidationError(String message,String[] details) {

        this.message=message;
        this.details = details;
    }

    /**
     * This constructor is used when we just need to set validation messages
     * @param messages
     */
    public ValidationError(String... messages) {
        this.message="";
        this.details = messages;
    }

    public String[] getDetails() {
        return details;
    }


    @Override
    public String toString()
    {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

}
