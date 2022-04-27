package de.superchat.crm.service.sender;

import de.superchat.crm.exception.UnsupportedPlatformException;

public class MessageSenderFactory {

    private MessageSenderFactory() {
    }

    /**
     * Selecting suitable implementation of MessageSenderAdapter based on the given platform
     * @param platform which need to be used for
     * @return suitable implementation of MessageSenderAdapter based on the given platform
     * @throws UnsupportedPlatformException when there is no implemented sender matched with the given platform
     */
   public static MessageSenderAdapter getMessageSenderByPlatform(String platform) throws UnsupportedPlatformException {

       switch (platform.toLowerCase())
       {
           case "email" : return new EmailMessageSenderAdapter();
           case "sms" : return new SmsMessageSenderAdapter();
           default: throw new UnsupportedPlatformException(String.format("Sending message to platform %s has not been implemented",platform));
       }

   }

}
