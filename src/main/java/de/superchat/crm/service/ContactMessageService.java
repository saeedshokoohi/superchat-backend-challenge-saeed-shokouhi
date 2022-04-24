package de.superchat.crm.service;

import de.superchat.crm.dto.ContactMessageDto;
import de.superchat.crm.dto.ExternalMessageDto;
import de.superchat.crm.repository.ContactMessageRepository;
import de.superchat.crm.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactMessageService {

    final
    ContactMessageRepository contactMessageRepository;
    final
    ContactRepository contactRepository;

    public ContactMessageService(ContactMessageRepository contactMessageRepository, ContactRepository contactRepository) {
        this.contactMessageRepository = contactMessageRepository;
        this.contactRepository = contactRepository;
    }

    public ContactMessageDto create(ContactMessageDto contactMessageDto)
    {
return null;
    }


    public void handleReceivedMessage(ExternalMessageDto message) {

    }
}
