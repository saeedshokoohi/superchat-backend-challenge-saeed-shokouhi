package de.superchat.crm.broker;

import de.superchat.crm.dto.MessageDto;
import de.superchat.crm.exception.InvalidModelException;
import de.superchat.crm.exception.SendingExternalMessageException;
import de.superchat.crm.exception.UnsupportedPlatformException;
import de.superchat.crm.service.ContactMessageService;
import de.superchat.crm.dispatcher.impl.MessageSenderService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import static de.superchat.crm.config.kafka.KafkaConstants.RECEIVED_MESSAGE_TOPIC;
import static de.superchat.crm.config.kafka.KafkaConstants.SEND_MESSAGE_TOPIC;

@Service
public class MessageConsumerService {
    Logger logger = LoggerFactory.getLogger(MessageConsumerService.class);

    final ContactMessageService contactMessageService;
    final MessageSenderService messageSenderService;

    public MessageConsumerService(ContactMessageService contactMessageService, MessageSenderService messageSenderService) {
        this.contactMessageService = contactMessageService;
        this.messageSenderService = messageSenderService;
    }


    /**
     * handles messages on kafka on RECEIVED_MESSAGE_TOPIC
     * it adds the external message to contactMessages
     * @param cr
     * @param message
     */
    @KafkaListener(topics = RECEIVED_MESSAGE_TOPIC)
    public void consumeReceivedMessageEvents(ConsumerRecord<String, String> cr, @Payload MessageDto message) {
        try {
            contactMessageService.receiveExternalMessage(message);
        } catch (InvalidModelException e) {
            logger.error("Error on consuming received message :");
           logger.error(e.getMessage());
        }
    }

    /**
     * handles messages on kafka on SEND_MESSAGE_TOPIC
     * tries to send message to external platform
     * @param cr
     * @param message
     */
    @KafkaListener(topics = SEND_MESSAGE_TOPIC)
    public void consumeSentMessageEvents(ConsumerRecord<String, String> cr, @Payload MessageDto message) {

        try {
            //sending message to external platform
            messageSenderService.sendMessageToTargetPlatform(message);
        } catch (UnsupportedPlatformException e)
        {
            logger.error("Error on sending  message to external platform :");
            logger.error(e.getMessage());

            //ToDo : Emit error message on kafka
        }
        catch (SendingExternalMessageException e) {
            logger.error("Error on sending  message to external platform :");
            logger.error(e.getMessage());
            //ToDo : Emit error message on kafka
        }

    }

}
