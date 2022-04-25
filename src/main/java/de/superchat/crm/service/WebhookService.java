package de.superchat.crm.service;

import de.superchat.crm.dto.ExternalMessageDto;
import de.superchat.crm.exception.InvalidModelException;
import org.springframework.stereotype.Service;

@Service
public class WebhookService {

    final ContactMessageService contactMessageService;

    public WebhookService(ContactMessageService contactMessageService) {
        this.contactMessageService = contactMessageService;
    }

    public void receiveMessage(ExternalMessageDto message) throws InvalidModelException {
        contactMessageService.receiveExternalMessage(message);
    }


}
