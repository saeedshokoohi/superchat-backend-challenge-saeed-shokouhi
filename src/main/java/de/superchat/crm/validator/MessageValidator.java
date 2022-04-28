package de.superchat.crm.validator;

import de.superchat.crm.dto.ContactDto;
import de.superchat.crm.dto.MessageDto;
import de.superchat.crm.exception.InvalidModelException;

import java.util.ArrayList;
import java.util.List;

public class MessageValidator {

    private static final String MESSAGE_IS_NULL = "MESSAGE_IS_NULL";
    private static final String MESSAGE_PLATFORM_IS_REQUIRED = "MESSAGE_PLATFORM_IS_REQUIRED";
    private static final String MESSAGE_PLATFORM_USER_ID_IS_REQUIRED = "MESSAGE_PLATFORM_USER_ID_IS_REQUIRED";
    private static final String MESSAGE_CONTENT_IS_REQUIRED = "MESSAGE_CONTENT_IS_REQUIRED";

    public static void validateReceivedMessages(MessageDto message) throws InvalidModelException
    {
        if(message==null)  throw new InvalidModelException(MESSAGE_IS_NULL);
        List<String> validationMessages=new ArrayList<>();
        if(message.getPlatform()==null) validationMessages.add(MESSAGE_PLATFORM_IS_REQUIRED);
        if(message.getPlatformUserId()==null) validationMessages.add(MESSAGE_PLATFORM_USER_ID_IS_REQUIRED);
        if(message.getMessage()==null) validationMessages.add(MESSAGE_CONTENT_IS_REQUIRED);


        if(!validationMessages.isEmpty())
            throw new InvalidModelException("Invalid message ",validationMessages.toArray(new String[0]));
    }

}
