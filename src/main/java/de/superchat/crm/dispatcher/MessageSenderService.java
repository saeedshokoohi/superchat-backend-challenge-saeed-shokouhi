package de.superchat.crm.dispatcher;

import de.superchat.crm.dto.MessageDto;
import de.superchat.crm.exception.SendingExternalMessageException;
import de.superchat.crm.exception.UnsupportedPlatformException;
import org.springframework.stereotype.Service;

/**
 * This service is responsible for dispatching the messages to the external platform
 */
@Service
public class MessageSenderService {

    /**
     * Sending message based on the message platform
     * @param message
     */
    public void sendMessageToTargetPlatform(MessageDto message) throws UnsupportedPlatformException, SendingExternalMessageException {

        if(message!=null)
        {
            MessageDispatcher sender = MessageDispatcherFactory.getMessageDispatcherByPlatform(message.getPlatform());
            sender.sendMessage(message);
        }
    }
}
