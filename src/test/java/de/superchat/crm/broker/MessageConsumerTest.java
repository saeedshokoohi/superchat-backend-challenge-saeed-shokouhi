package de.superchat.crm.broker;


import de.superchat.crm.config.kafka.KafkaConstants;
import de.superchat.crm.dispatcher.MessageSenderService;
import de.superchat.crm.dto.MessageDto;
import de.superchat.crm.exception.InvalidModelException;
import de.superchat.crm.exception.SendingExternalMessageException;
import de.superchat.crm.exception.UnsupportedPlatformException;
import de.superchat.crm.service.ContactMessageService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:39093", "port=39093" })
@TestPropertySource("classpath:application-test.yml")
 class MessageConsumerTest {

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @MockBean
    ContactMessageService contactMessageService;

    @MockBean
    MessageSenderService messageSenderService;

    @Test
     void consumeReceivedMessageEventsTest() throws InvalidModelException {
        //given

        MessageDto messageDto=new MessageDto();
        messageDto.setMessage("TEST");
        messageDto.setPlatform("SMS");
        messageDto.setPlatformUserId("+9886656566");

        kafkaTemplate.send(KafkaConstants.RECEIVED_MESSAGE_TOPIC, messageDto);

        //then
        ArgumentCaptor<MessageDto> argumentCaptor = ArgumentCaptor.forClass(MessageDto.class);
        verify(contactMessageService,timeout(10_000).atLeast(1)).receiveExternalMessage(argumentCaptor.capture());

        MessageDto paramMessage = argumentCaptor.getValue();

        assertEquals(paramMessage.getMessage(),messageDto.getMessage());
        assertEquals(paramMessage.getPlatform(),messageDto.getPlatform());
        assertEquals(paramMessage.getPlatformUserId(),messageDto.getPlatformUserId());

    }

    @Test
     void consumeSentMessageEventsTest() throws  UnsupportedPlatformException, SendingExternalMessageException {
        //given

        MessageDto messageDto=new MessageDto();
        messageDto.setMessage("TEST");
        messageDto.setPlatform("SMS");
        messageDto.setPlatformUserId("+9886656566");

        kafkaTemplate.send(KafkaConstants.SEND_MESSAGE_TOPIC, messageDto);

        //then
        ArgumentCaptor<MessageDto> argumentCaptor = ArgumentCaptor.forClass(MessageDto.class);
        verify(messageSenderService,timeout(10_000).atLeast(1)).sendMessageToTargetPlatform(argumentCaptor.capture());

        MessageDto paramMessage = argumentCaptor.getValue();

        assertEquals(paramMessage.getMessage(),messageDto.getMessage());
        assertEquals(paramMessage.getPlatform(),messageDto.getPlatform());
        assertEquals(paramMessage.getPlatformUserId(),messageDto.getPlatformUserId());

    }


}
