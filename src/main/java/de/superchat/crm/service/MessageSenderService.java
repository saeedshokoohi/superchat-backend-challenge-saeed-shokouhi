package de.superchat.crm.service;

import de.superchat.crm.dto.MessageDto;
import de.superchat.crm.exception.SendingExternalMessageException;
import de.superchat.crm.exception.UnsupportedPlatformException;
import de.superchat.crm.service.sender.MessageSenderAdapter;
import de.superchat.crm.service.sender.MessageSenderFactory;
import org.springframework.stereotype.Service;

@Service
public class MessageSenderService {

    /**
     * Sending message based on the message platform
     * @param message
     */
    public void sendMessageToTargetPlatform(MessageDto message) throws UnsupportedPlatformException, SendingExternalMessageException {

        if(message!=null)
        {
            MessageSenderAdapter sender = MessageSenderFactory.getMessageSenderByPlatform(message.getPlatform());
            sender.sendMessage(message);
        }
    }
}
