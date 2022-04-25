package de.superchat.crm.service.placeholder;

import de.superchat.crm.exception.PlaceholderHandlingException;

public class ContactNamePlaceholderFiller implements PlaceholderFiller {

    private static  final String PLACEHOLDER_REGX ="${contactname}";
    private final String contactName;

    public ContactNamePlaceholderFiller(String contactName) {
        this.contactName = contactName;
    }


    @Override
    public String fillPlaceholder(String originalMessage) throws PlaceholderHandlingException {
        if(originalMessage==null)return null;

       return originalMessage.replace(PLACEHOLDER_REGX,this.contactName);
    }
}
