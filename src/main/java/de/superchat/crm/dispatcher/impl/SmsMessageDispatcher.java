package de.superchat.crm.dispatcher.impl;

import de.superchat.crm.dispatcher.MessageDispatcher;
import de.superchat.crm.dto.MessageDto;
import de.superchat.crm.exception.SendingExternalMessageException;


/**
 * It is a mock implementation of MessageDispatcher when platform is Email
 */
public class SmsMessageDispatcher implements MessageDispatcher {
    @Override
    public boolean sendMessage(MessageDto message) throws SendingExternalMessageException {
        try{
            //ToDo : Using SMS api to send message
            System.out.println(String.format("%s sent to %s successfully by %s service.",message.getMessage(),message.getPlatformUserId(),message.getPlatform()));
        }
        catch (Exception ex)
        {
            throw new SendingExternalMessageException(ex.getMessage());
        }
        return true;
    }
}
