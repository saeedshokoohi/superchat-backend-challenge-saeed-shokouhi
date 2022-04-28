package de.superchat.crm.dispatcher.impl;

import de.superchat.crm.dispatcher.MessageDispatcher;
import de.superchat.crm.dto.MessageDto;
import de.superchat.crm.exception.SendingExternalMessageException;

/**
 * It is a mock implementation of MessageSender when platform is Email
 */
public class EmailMessageDispatcher implements MessageDispatcher {
    @Override
    public boolean sendMessage(MessageDto message) throws SendingExternalMessageException {
        try{
            //ToDo : Using Email service to send message email
            System.out.println(String.format("%s sent to %s successfully.",message.getMessage(),message.getPlatformUserId()));
        }
        catch (Exception ex)
        {
            throw new SendingExternalMessageException(ex.getMessage());
        }
        return true;
    }
}
