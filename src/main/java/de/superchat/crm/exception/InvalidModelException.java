package de.superchat.crm.exception;

import java.util.Arrays;

/**
 * An Exception class that handles Model validation details
 */
public class InvalidModelException extends Exception {
    private final String fieldName;
    private final String[] validationMessages;


    /**
     * This constructor is used when we need to set the field and multiple validation messages for the field
     * @param fieldName the field that validation messages is related to
     * @param messages the validation messages
     */
    public InvalidModelException(String fieldName,String[] messages) {
        super();
        this.fieldName=fieldName;
        this.validationMessages = messages;
    }

    /**
     * This constructor is used when we just need to set validation messages
     * @param messages
     */
    public InvalidModelException(String... messages) {
        super();
        this.fieldName=null;
        this.validationMessages = messages;
    }

    public String[] getValidationMessages() {
        return validationMessages;
    }

    public String getFieldName() {
        return fieldName;
    }

    @Override
    public String getMessage()
    {
        return this.toString();
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder=new StringBuilder();
        if(this.fieldName!=null) stringBuilder.append(String.format("\n %s : ",fieldName));
        if(validationMessages!=null)
            Arrays.stream(this.validationMessages).forEach(sm->stringBuilder.append(String.format("\n\t %s  ",sm)));
        return stringBuilder.toString();
    }
}
