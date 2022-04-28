package de.superchat.crm.service;

import de.superchat.crm.dto.*;
import de.superchat.crm.dto.mapper.ContactMessageMapper;
import de.superchat.crm.entity.Contact;
import de.superchat.crm.entity.ContactMessage;
import de.superchat.crm.entity.MessageContent;
import de.superchat.crm.entity.enums.MessageDirection;
import de.superchat.crm.entity.enums.MessageStatus;
import de.superchat.crm.exception.InvalidModelException;
import de.superchat.crm.exception.PlaceholderHandlingException;
import de.superchat.crm.placeholder.PlaceholderService;
import de.superchat.crm.repository.ContactMessageRepository;
import de.superchat.crm.repository.ContactRepository;
import de.superchat.crm.util.DateTimeUtil;
import de.superchat.crm.validator.MessageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static de.superchat.crm.validator.ContactValidator.CONTACT_ID_IS_NOT_VALID;

@Service
public class ContactMessageService {
    public static final int MESSAGE_PREVIEW_MAX_LENGTH = 50;

    private final ContactMessageRepository contactMessageRepository;
    private final ContactService contactService;
    private final PlaceholderService placeholderService;


    public ContactMessageService(ContactMessageRepository contactMessageRepository,  ContactService contactService, PlaceholderService placeholderService) {
        this.contactMessageRepository = contactMessageRepository;
        this.contactService = contactService;
        this.placeholderService = placeholderService;
    }

    /**
     * Send text message to contact
     *
     * @param contactId receiver contactId
     * @param message   the message to send
     * @return saved message detail
     * @throws InvalidModelException        is validation error
     * @throws PlaceholderHandlingException if any exception in placeholder filling
     */
    public ContactMessageDto sendTextMessage(long contactId, String message) throws InvalidModelException, PlaceholderHandlingException {
        //getting contact by email
        Optional<Contact> contact = contactService.findById(contactId);
        if (contact.isEmpty()) {
            throw new InvalidModelException("Invalid ContactId", CONTACT_ID_IS_NOT_VALID);
        } else {
            //creating ContactMessage entity
            ContactMessage contactMessage = createContactTextMessage(contact.get(), message);
            contactMessage.setMessageStatus(MessageStatus.PENDING);
            contactMessage.setDirection(MessageDirection.OUT);
            //applying placeholders
            placeholderService.applyPlaceholders(contactMessage);
            contactMessage.setMessagePreview(makeTextMessagePreview(contactMessage.getMessageContent().getContent()));
            return ContactMessageMapper.toDto(this.contactMessageRepository.save(contactMessage));
        }

    }


    /**
     * receiving external message in persisting the message is IN message
     *
     * @param message received message
     * @throws InvalidModelException if received message has validation error
     */
    public void receiveExternalMessage(MessageDto message) throws InvalidModelException {

        MessageValidator.validateReceivedMessages(message);
        Contact contact = contactService.getOrCreateContact(new ContactDto().setClientId(message.getPlatformUserId()).setClientPlatform(message.getPlatform()));
        //creating ContactMessage entity
        ContactMessage contactMessage = createContactTextMessage(contact, message.getMessage());
        contactMessage.setMessageStatus(MessageStatus.DELIVERED);
        contactMessage.setDirection(MessageDirection.IN);

        contactMessage.setMessagePreview(makeTextMessagePreview(message.getMessage()));
        this.contactMessageRepository.save(contactMessage);

    }

    /**
     * fetching all messages related to contact id as Conversation object
     *
     * @param contactId
     * @return
     */
    public ConversationDto conversationByContactId(long contactId) {
        Optional<ContactDto> contact = contactService.getContactById(contactId);
        List<BasicMessageDto> messages =null;
        if (contact.isPresent()) {
            messages=  contactMessageRepository.findAllByContactId(contactId).stream().map(ContactMessageMapper::toBasicMessage).collect(Collectors.toList());
        }
        return new ConversationDto(contact.orElse(null), messages);
    }


    /**
     * wrapping a ContactMessage object using contact and message
     *
     * @param contact relevant contact
     * @param message text message
     * @return initiated ContactMessage
     */
    private ContactMessage createContactTextMessage(Contact contact, String message) {
        ContactMessage contactMessage = new ContactMessage();
        contactMessage.setContact(contact);
        contactMessage.setMessageContent(new MessageContent(message));
        contactMessage.setDateCreated(DateTimeUtil.now());

        return contactMessage;

    }

    /**
     * making a preview message from full message
     * @param message is the original message
     * @return
     */
    private String makeTextMessagePreview(String message) {
        return message==null?"": limitedMessageLength(message, MESSAGE_PREVIEW_MAX_LENGTH);
    }

    /**
     * shrinking the message based on the given maxLength
     * @param message the original message
     * @param maxLength
     * @return
     */
    private String limitedMessageLength(String message, int maxLength)
    {
        return  message.length()>maxLength? message.substring(0,maxLength-1)+" ...": message;
    }

}
