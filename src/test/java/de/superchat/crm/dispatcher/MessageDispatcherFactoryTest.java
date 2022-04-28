package de.superchat.crm.dispatcher;

import de.superchat.crm.dispatcher.impl.EmailMessageDispatcher;
import de.superchat.crm.dispatcher.impl.SmsMessageDispatcher;
import de.superchat.crm.exception.SendingExternalMessageException;
import de.superchat.crm.exception.UnsupportedPlatformException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageDispatcherFactoryTest {
    @Test
    void testGetMessageDispatcherByPlatformWhenEmail() throws SendingExternalMessageException, UnsupportedPlatformException {
       //given
        EmailMessageDispatcher emailMessageDispatcher=new EmailMessageDispatcher();
        //when
        MessageDispatcher actualMessageDispatcherByPlatform = MessageDispatcherFactory
                .getMessageDispatcherByPlatform("email");
        //then
        assertEquals(emailMessageDispatcher.getClass(),actualMessageDispatcherByPlatform.getClass());

         }

    @Test
    void testGetMessageDispatcherByPlatformWhenSms() throws SendingExternalMessageException, UnsupportedPlatformException {
        //given
        SmsMessageDispatcher smsMessageDispatcher=new SmsMessageDispatcher();
        //when
        MessageDispatcher actualMessageDispatcherByPlatform = MessageDispatcherFactory
                .getMessageDispatcherByPlatform("Sms");
        //then
        assertEquals(smsMessageDispatcher.getClass(),actualMessageDispatcherByPlatform.getClass());  }

    @Test
    void testGetMessageDispatcherByPlatformWhenInvalidPlatform() throws SendingExternalMessageException, UnsupportedPlatformException {

        assertThrows(UnsupportedPlatformException.class,()->{
            MessageDispatcher actualMessageDispatcherByPlatform = MessageDispatcherFactory
                    .getMessageDispatcherByPlatform("invalidPlatform");
        });
    }
}

