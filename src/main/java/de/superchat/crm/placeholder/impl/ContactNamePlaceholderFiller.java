package de.superchat.crm.placeholder.impl;

import de.superchat.crm.exception.PlaceholderHandlingException;
import de.superchat.crm.placeholder.MessageContext;
import de.superchat.crm.placeholder.PlaceholderFiller;

public class ContactNamePlaceholderFiller implements PlaceholderFiller {

    private static  final String PLACEHOLDER_REGX ="${contactname}";


    @Override
    public String fillPlaceholder(MessageContext context,String originalMessage) throws PlaceholderHandlingException {
        if(originalMessage==null || context==null || context.getContact()==null)return originalMessage;
       return originalMessage.replace(PLACEHOLDER_REGX,context.getContact().getFullName());
    }
}
