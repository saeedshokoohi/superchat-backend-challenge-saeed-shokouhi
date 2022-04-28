package de.superchat.crm.service;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.superchat.crm.broker.MessageProducerService;
import de.superchat.crm.dto.MessageDto;
import de.superchat.crm.exception.InvalidModelException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WebhookService.class, MessageProducerService.class})
@ExtendWith(SpringExtension.class)
class WebhookServiceTest {
    @MockBean
    private MessageProducerService messageProducerService;

    @Autowired
    private WebhookService webhookService;



    @Test
    void testReceiveMessage() throws InvalidModelException {
        //given
        doNothing().when(this.messageProducerService).emitReceivedMessageEvent((MessageDto) any());
        MessageDto messageDto = mock(MessageDto.class);
        when(messageDto.getMessage()).thenReturn("Not all who wander are lost");
        when(messageDto.getPlatformUserId()).thenReturn("42");
        when(messageDto.getPlatform()).thenReturn("Platform");

        //when
        this.webhookService.receiveMessage(messageDto);

        //then
        verify(this.messageProducerService).emitReceivedMessageEvent((MessageDto) any());
        verify(messageDto).getMessage();
        verify(messageDto).getPlatform();
        verify(messageDto).getPlatformUserId();
    }
}

