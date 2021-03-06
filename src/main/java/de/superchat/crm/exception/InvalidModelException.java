package de.superchat.crm.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.beans.Transient;


/**
 * An Exception class that handles Model validation details
 */
public class InvalidModelException extends Throwable {


   private final ValidationError errors;
    /**
     * This constructor is used when we need to set the field and multiple validation messages for the field
     * @param message the field that validation messages is related to
     * @param details the validation messages
     */
    public InvalidModelException(String message,String[] details) {

        this.errors = new ValidationError(message,details);
    }

    /**
     * This constructor is used when we just need to set validation messages
     * @param messages
     */
    public InvalidModelException(String... messages) {

       this.errors= new ValidationError(messages);
    }


    public String[] getValidationMessages() {
        return this.errors!=null ? this.errors.getDetails():new String[0];
    }

    @Override
    public String toString()
    {
        ObjectMapper mapper = new ObjectMapper();
        try {
           return mapper.writeValueAsString(this.errors);
        } catch (JsonProcessingException e) {
           return "";
        }
    }


}
