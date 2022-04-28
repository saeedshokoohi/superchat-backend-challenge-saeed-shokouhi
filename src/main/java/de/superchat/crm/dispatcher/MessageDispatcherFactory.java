package de.superchat.crm.dispatcher;

import de.superchat.crm.dispatcher.impl.EmailMessageDispatcher;
import de.superchat.crm.exception.UnsupportedPlatformException;

public class MessageDispatcherFactory {

    private MessageDispatcherFactory() {
    }

    /**
     * Selecting suitable implementation of MessageDispatcher based on the given platform
     * @return suitable implementation of MessageDispatcher based on the given platform
     * @throws UnsupportedPlatformException when there is no implemented sender matched with the given platform
     */
   public static MessageDispatcher getMessageSenderByPlatform(String platform) throws UnsupportedPlatformException {

       switch (platform.toLowerCase())
       {
           case "email" : return new EmailMessageDispatcher();
           case "sms" : return new SmsMessageDispatcher();
           default: throw new UnsupportedPlatformException(String.format("Sending message to platform %s has not been implemented",platform));
       }

   }

}
