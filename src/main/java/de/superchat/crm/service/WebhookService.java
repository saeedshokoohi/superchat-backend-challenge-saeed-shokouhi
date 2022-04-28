package de.superchat.crm.service;

import de.superchat.crm.broker.MessageProducerService;
import de.superchat.crm.dto.MessageDto;
import de.superchat.crm.exception.InvalidModelException;
import de.superchat.crm.validator.MessageValidator;
import org.springframework.stereotype.Service;

@Service
public class WebhookService {

    final
    MessageProducerService contactMessageService;

    public WebhookService(MessageProducerService contactMessageService) {
        this.contactMessageService = contactMessageService;
    }

    /**
     * checking the message validation and sending to message broker
     * @param message
     * @throws InvalidModelException
     */
    public void receiveMessage(MessageDto message) throws InvalidModelException {
        MessageValidator.validateReceivedMessages(message);
        contactMessageService.emitReceivedMessageEvent(message);
    }

}
