package de.superchat.crm.broker;

import de.superchat.crm.config.kafka.KafkaConstants;
import de.superchat.crm.dto.MessageDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * This service is responsible emitting message on message broker
 */
@Service
public class MessageProducerService {

    //injection kafka template using for emitting message
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public MessageProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * firing an event message which a message has been received
     *
     * @param msg
     */
    public void emitReceivedMessageEvent(MessageDto msg) {
        kafkaTemplate.send(KafkaConstants.RECEIVED_MESSAGE_TOPIC, msg);
    }

    /**
     * firing and event message that a new message has been sent out
     *
     * @param msg
     */
    public void emitSentMessageEvent(MessageDto msg) {
        kafkaTemplate.send(KafkaConstants.RECEIVED_MESSAGE_TOPIC, msg);
    }

}
