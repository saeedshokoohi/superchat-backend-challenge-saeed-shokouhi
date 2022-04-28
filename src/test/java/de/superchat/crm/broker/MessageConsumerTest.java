package de.superchat.crm.broker;


import de.superchat.crm.config.kafka.KafkaConstants;
import de.superchat.crm.dispatcher.impl.MessageSenderService;
import de.superchat.crm.dto.MessageDto;
import de.superchat.crm.exception.InvalidModelException;
import de.superchat.crm.exception.SendingExternalMessageException;
import de.superchat.crm.exception.UnsupportedPlatformException;
import de.superchat.crm.service.ContactMessageService;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:29092", "port=29092" })
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
        verify(contactMessageService,timeout(1000)).receiveExternalMessage(argumentCaptor.capture());

        MessageDto paramMessage = argumentCaptor.getValue();

        assertEquals(paramMessage.getMessage(),messageDto.getMessage());
        assertEquals(paramMessage.getPlatform(),messageDto.getPlatform());
        assertEquals(paramMessage.getPlatformUserId(),messageDto.getPlatformUserId());

    }

    @Test
    public void consumeSentMessageEventsTest() throws  UnsupportedPlatformException, SendingExternalMessageException {
        //given

        MessageDto messageDto=new MessageDto();
        messageDto.setMessage("TEST");
        messageDto.setPlatform("SMS");
        messageDto.setPlatformUserId("+9886656566");

        kafkaTemplate.send(KafkaConstants.SEND_MESSAGE_TOPIC, messageDto);

        //then
        ArgumentCaptor<MessageDto> argumentCaptor = ArgumentCaptor.forClass(MessageDto.class);
        verify(messageSenderService,timeout(1000)).sendMessageToTargetPlatform(argumentCaptor.capture());

        MessageDto paramMessage = argumentCaptor.getValue();

        assertEquals(paramMessage.getMessage(),messageDto.getMessage());
        assertEquals(paramMessage.getPlatform(),messageDto.getPlatform());
        assertEquals(paramMessage.getPlatformUserId(),messageDto.getPlatformUserId());

    }


}
