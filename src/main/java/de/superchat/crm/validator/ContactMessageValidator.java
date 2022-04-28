package de.superchat.crm.validator;

import de.superchat.crm.dto.ContactDto;
import de.superchat.crm.exception.InvalidModelException;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains stateless validation logic for SaleObjects
 *
 */
public class ContactMessageValidator {


    private static final String CONTACT_IS_NULL = "CONTACT_IS_NULL";
    public static final String EMAIL_IS_REQUIRED = "EMAIL_IS_REQUIRED";
    public static final String LAST_NAME_IS_REQUIRED = "LAST_NAME_IS_REQUIRED";
    public static final String NAME_IS_REQUIRED = "NAME_IS_REQUIRED";
    public static final String EMAIL_IS_NOT_A_VALID_EMAIL_ADDRESS = "EMAIL_IS_NOT_A_VALID_EMAIL_ADDRESS";

    private ContactMessageValidator() {
    }

    /**
     * Validating the given Contact
     * @param contact to validate
     * @throws InvalidModelException if object is not valid and the message shows the reason
     */
    public static void validateContactMessage(ContactDto contact) throws InvalidModelException
    {
        if(contact==null)  throw new InvalidModelException(CONTACT_IS_NULL);
        List<String> validationMessages=new ArrayList<>();
        if(contact.getEmail()==null) validationMessages.add(EMAIL_IS_REQUIRED);
        if(contact.getLastName()==null) validationMessages.add(LAST_NAME_IS_REQUIRED);
        if(contact.getName()==null) validationMessages.add(NAME_IS_REQUIRED);
        if(!isEmailValid(contact.getEmail()))validationMessages.add(EMAIL_IS_NOT_A_VALID_EMAIL_ADDRESS);

        if(!validationMessages.isEmpty())
            throw new InvalidModelException("Invalid contact ",validationMessages.toArray(new String[0]));

    }

    /**
     * check if given email address is a valid email address
     * @param email address in String
     * @return true if email is null or valid
     */
    private static boolean isEmailValid(String email) {
        if(email==null)return true;
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();

    }

}
