package de.superchat.crm.service;

import de.superchat.crm.broker.MessageProducerService;
import de.superchat.crm.dto.ExternalMessageDto;
import de.superchat.crm.dto.MessageDto;
import de.superchat.crm.exception.InvalidModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebhookService {

    final
    MessageProducerService contactMessageService;

    public WebhookService(MessageProducerService contactMessageService) {
        this.contactMessageService = contactMessageService;
    }


    public void receiveMessage(MessageDto message) throws InvalidModelException {
  contactMessageService.emitReceivedMessageEvent(message);
    }


}
