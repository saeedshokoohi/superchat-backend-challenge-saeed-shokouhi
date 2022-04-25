package de.superchat.crm.service;

import de.superchat.crm.dto.ContactMessageDto;
import de.superchat.crm.dto.ContactMessageListDto;
import de.superchat.crm.dto.ExternalMessageDto;
import de.superchat.crm.dto.mapper.ContactMessageMapper;
import de.superchat.crm.entity.Contact;
import de.superchat.crm.entity.ContactMessage;
import de.superchat.crm.entity.MessageContent;
import de.superchat.crm.entity.enums.MessageDirection;
import de.superchat.crm.entity.enums.MessageStatus;
import de.superchat.crm.exception.InvalidModelException;
import de.superchat.crm.exception.PlaceholderHandlingException;
import de.superchat.crm.repository.ContactMessageRepository;
import de.superchat.crm.repository.ContactRepository;
import de.superchat.crm.util.DateTimeUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static de.superchat.crm.validator.ContactValidator.THERE_IS_NO_CONTACT_WITH_GIVEN_EMAIL_ADDRESS;

@Service
public class ContactMessageService {

    public static final String SUPERCHAT = "SUPERCHAT";
    final ContactMessageRepository contactMessageRepository;
    final ContactRepository contactRepository;
    final MessageContentService messageContentService;

    public ContactMessageService(ContactMessageRepository contactMessageRepository, ContactRepository contactRepository, MessageContentService messageContentService) {
        this.contactMessageRepository = contactMessageRepository;
        this.contactRepository = contactRepository;
        this.messageContentService = messageContentService;
    }

    /**
     * Send text message to contact
     * @param sendToEmail receiver contact email
     * @param message the message to send
     * @return saved message detail
     * @throws InvalidModelException is validation error
     * @throws PlaceholderHandlingException if any exception in placeholder filling
     */
    public ContactMessageDto sendTextMessage(String sendToEmail, String message) throws InvalidModelException, PlaceholderHandlingException {
        //getting contact by email
       Optional<Contact> contact=contactRepository.findByEmail(sendToEmail);
       if(contact.isEmpty()) { throw new InvalidModelException("Email", THERE_IS_NO_CONTACT_WITH_GIVEN_EMAIL_ADDRESS);}
       else {
           //creating ContactMessage entity
           ContactMessage contactMessage = createContactTextMessage(contact.get(), message);
           contactMessage.setMessageStatus(MessageStatus.SENT);
           contactMessage.setDirection(MessageDirection.OUT);
           contactMessage.setMessageSource(SUPERCHAT);
           //applying placeholders
           messageContentService.applyPlaceholders(contactMessage);
           contactMessage.setMessagePreview(messageContentService.makeTextMessagePreview(contactMessage.getMessageContent().getContent()));
           return ContactMessageMapper.toDto(this.contactMessageRepository.save(contactMessage));
       }

    }

    /**
     * receiving external message in persisting the message is IN message
     * @param message received message
     * @throws InvalidModelException if received message has validation error
     */
    public void receiveExternalMessage(ExternalMessageDto message) throws InvalidModelException {
        //getting contact by email
        Optional<Contact> contact=contactRepository.findByEmail(message.getSenderEmail());
        if(contact.isEmpty())throw new InvalidModelException("Email",THERE_IS_NO_CONTACT_WITH_GIVEN_EMAIL_ADDRESS);
        //creating ContactMessage entity
        ContactMessage contactMessage=createContactTextMessage(contact.get(), message.getMessage());
        contactMessage.setMessageStatus(MessageStatus.DELIVERED);
        contactMessage.setDirection(MessageDirection.IN);
        contactMessage.setMessageSource(message.getSource());
        contactMessage.setMessagePreview(messageContentService.makeTextMessagePreview(message.getMessage()));
        this.contactMessageRepository.save(contactMessage);

    }

    /**
     * Get List of messages by contact email
     * @param email
     * @return
     */
    public ContactMessageListDto messageListByEmail(String email) {
        List<ContactMessage> contactMessages = contactMessageRepository.findByContactEmail(email);
        return new ContactMessageListDto(contactMessages.stream().map(ContactMessageMapper::toDto).collect(Collectors.toList()));
    }

    /**
     * wrapping a ContactMessage object using contact and message
     * @param contact relevant contact
     * @param message text message
     * @return initiated ContactMessage
     */
    private ContactMessage createContactTextMessage(Contact contact, String message) {
        ContactMessage contactMessage=new ContactMessage();
        contactMessage.setContact(contact);

        contactMessage.setMessageContent(new MessageContent(message));
        contactMessage.setDateCreated(DateTimeUtil.now());

        return contactMessage;

    }


}
