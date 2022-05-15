package de.superchat.crm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.superchat.crm.broker.MessageProducerService;
import de.superchat.crm.dto.ContactDto;
import de.superchat.crm.dto.ContactMessageDto;
import de.superchat.crm.dto.ConversationDto;
import de.superchat.crm.dto.MessageDto;
import de.superchat.crm.dto.api.SendMessageDto;
import de.superchat.crm.entity.Contact;
import de.superchat.crm.entity.ContactMessage;
import de.superchat.crm.entity.MessageContent;
import de.superchat.crm.entity.enums.ContactStatus;
import de.superchat.crm.entity.enums.MessageContentType;
import de.superchat.crm.entity.enums.MessageDirection;
import de.superchat.crm.entity.enums.MessageStatus;
import de.superchat.crm.exception.InvalidModelException;
import de.superchat.crm.exception.PlaceholderHandlingException;
import de.superchat.crm.placeholder.PlaceholderFiller;
import de.superchat.crm.placeholder.PlaceholderService;
import de.superchat.crm.placeholder.impl.BitcoinPricePlaceholderFiller;
import de.superchat.crm.placeholder.impl.BitcoinPriceService;
import de.superchat.crm.placeholder.impl.ContactNamePlaceholderFiller;
import de.superchat.crm.repository.ContactMessageRepository;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ContactMessageService.class, ContactService.class, PlaceholderService.class, MessageProducerService.class})
@ExtendWith(SpringExtension.class)
class ContactMessageServiceTest {
    @MockBean
    private ContactMessageRepository contactMessageRepository;

    @Autowired
    private ContactMessageService contactMessageService;

    @MockBean
    private ContactService contactService;

    @MockBean
    private PlaceholderService placeholderService;
    @MockBean
    MessageProducerService messageProducerService;

    @MockBean
    BitcoinPriceService bitcoinPriceService;

    Set<PlaceholderFiller> registeredPlaceHolderFillers;
    @BeforeEach
     void  init()
    {
        registeredPlaceHolderFillers =new HashSet<>();
        registeredPlaceHolderFillers.add(new BitcoinPricePlaceholderFiller(bitcoinPriceService));
        registeredPlaceHolderFillers.add(new ContactNamePlaceholderFiller());

    }

    @Test
    void testSendTextMessage() throws InvalidModelException, PlaceholderHandlingException {

        //given
        String message = "Hello ";
        Contact opContact = getSampleContact();
        MessageContent messageContent = getSampleMessageContent(message);
        when(this.contactService.findById(anyLong())).thenReturn(Optional.of(opContact));

        ContactMessage contactMessage = getSampleContactMessage(message, messageContent, opContact);

        doNothing().when(this.placeholderService).applyPlaceholders((ContactMessage) any(),any());
        when(this.contactMessageRepository.save((ContactMessage) any())).thenReturn(contactMessage);

        SendMessageDto sendMessageDto = new SendMessageDto();
        sendMessageDto.setMessage(message);
        sendMessageDto.setContactId(opContact.getId());
        contactMessageService.registerPlaceHolderFiller(registeredPlaceHolderFillers.toArray(PlaceholderFiller[]::new));

        //when
        ContactMessageDto actualSendTextMessageResult = this.contactMessageService.sendTextMessage(sendMessageDto);

        //then
        assertEquals("1970-01-01T00:00:01", actualSendTextMessageResult.getDateCreated());
        assertEquals(123L, actualSendTextMessageResult.getId().longValue());
        assertEquals(MessageStatus.PENDING, actualSendTextMessageResult.getStatus());
        assertEquals(message, actualSendTextMessageResult.getMessage());
        assertEquals(MessageDirection.IN, actualSendTextMessageResult.getDirection());
        assertEquals(message, actualSendTextMessageResult.getMessagePreview());
        ContactDto contact2 = actualSendTextMessageResult.getContact();
        assertEquals(opContact.getClientId(), contact2.getClientId());
        assertEquals(opContact.getName(), contact2.getName());
        assertEquals(opContact.getEmail(), contact2.getEmail());
        assertEquals(opContact.getId(), contact2.getId().longValue());
        assertEquals(opContact.getLastName(), contact2.getLastName());
        assertEquals(opContact.getClientPlatform(), contact2.getClientPlatform());

        verify(this.placeholderService).applyPlaceholders((ContactMessage) any(),any());
        verify(this.contactService).findById(anyLong());
        verify(this.contactMessageRepository).save((ContactMessage) any());
    }

    @Test
    void testSendTextMessageWhenInvalidModel() throws InvalidModelException, PlaceholderHandlingException {

        //given
        String message = "Hello ";
        Contact opContact = getSampleContact();
        MessageContent messageContent = getSampleMessageContent(message);
        when(this.contactService.findById(anyLong())).thenReturn(Optional.empty());

        SendMessageDto sendMessageDto = new SendMessageDto();
        sendMessageDto.setMessage(message);
        sendMessageDto.setContactId(opContact.getId());

        //then
        assertThrows(InvalidModelException.class, () -> {
            this.contactMessageService.sendTextMessage(sendMessageDto);
        });
        verify(this.contactService).findById(anyLong());
    }

    @Test
    void testReceiveExternalMessage() throws InvalidModelException {

        //when
        Contact contact = getSampleContact();
        when(this.contactService.getOrCreateContact((de.superchat.crm.dto.ContactDto) any())).thenReturn(contact);

        MessageContent messageContent = new MessageContent();
        messageContent.setContentType(MessageContentType.TEXT);
        messageContent.setId(123L);
        messageContent.setContent("Not all who wander are lost");

        ContactMessage contactMessage = new ContactMessage();
        contactMessage.setContact(contact);
        contactMessage.setMessageContent(messageContent);
        contactMessage.setId(123L);
        contactMessage.setMessagePreview("Message Preview");
        contactMessage.setDateCreated(1L);
        contactMessage.setDirection(MessageDirection.IN);
        contactMessage.setMessageStatus(MessageStatus.PENDING);
        when(this.contactMessageRepository.save((ContactMessage) any())).thenReturn(contactMessage);

        //when
        this.contactMessageService.receiveExternalMessage(new MessageDto("Platform", "42", "Not all who wander are lost"));

        //given
        verify(this.contactService).getOrCreateContact((de.superchat.crm.dto.ContactDto) any());
        verify(this.contactMessageRepository).save((ContactMessage) any());
    }


    @Test
    void testConversationByContactId() {

        //given
        String message="Hi";
        ContactDto contactDto = getSampleContactDto();
        Contact contact = getSampleContact();
        MessageContent messageContent = getSampleMessageContent(message);
        ContactMessage contactMessage = getSampleContactMessage(message,messageContent,contact);
        Optional<ContactDto> ofResult = Optional.<ContactDto>of(contactDto);

        when(this.contactService.getContactById(anyLong())).thenReturn(ofResult);

        ContactMessage contactMessage1 = new ContactMessage();
        contactMessage1.setContact(contact);
        contactMessage1.setMessageContent(messageContent);
        contactMessage1.setId(123L);
        contactMessage1.setMessagePreview("Message Preview");
        contactMessage1.setDateCreated(0L);
        contactMessage1.setDirection(MessageDirection.IN);
        contactMessage1.setMessageStatus(MessageStatus.PENDING);

        ArrayList<ContactMessage> contactMessageList = new ArrayList<ContactMessage>();
        contactMessageList.add(contactMessage1);
        contactMessageList.add(contactMessage);

        when(this.contactMessageRepository.findAllByContactId(anyLong())).thenReturn(contactMessageList);

        //when
        ConversationDto actualConversationByContactIdResult = this.contactMessageService.conversationByContactId(123L);

        //then
        assertSame(contactDto, actualConversationByContactIdResult.getContact());
        assertEquals(2, actualConversationByContactIdResult.getMessages().size());
        assertEquals(2, actualConversationByContactIdResult.getMessageCount());
        verify(this.contactService).getContactById(anyLong());
        verify(this.contactMessageRepository).findAllByContactId(anyLong());
    }

    private ContactDto getSampleContactDto() {
        ContactDto contactDto = new ContactDto();
        contactDto.setEmail("jane.doe@example.org");
        contactDto.setClientId("42");
        contactDto.setLastName("Doe");
        contactDto.setClientPlatform("Client Platform");
        contactDto.setName("Name");
        contactDto.setDateCreated(1L);
        contactDto.setId(123L);
        return contactDto;
    }

    private ContactMessage getSampleContactMessage(String message, MessageContent messageContent, Contact contact) {
        ContactMessage contactMessage = new ContactMessage();
        contactMessage.setContact(contact);
        contactMessage.setMessageContent(messageContent);
        contactMessage.setId(123L);
        contactMessage.setMessagePreview(message);
        contactMessage.setDateCreated(1L);
        contactMessage.setDirection(MessageDirection.IN);
        contactMessage.setMessageStatus(MessageStatus.PENDING);
        return contactMessage;
    }

    private MessageContent getSampleMessageContent(String message) {
        MessageContent messageContent = new MessageContent();
        messageContent.setContentType(MessageContentType.TEXT);
        messageContent.setId(123L);
        messageContent.setContent(message);
        return messageContent;
    }

    private Contact getSampleContact() {
        Contact contact = new Contact();
        contact.setLastName("Doe");
        contact.setEmail("jane.doe@example.org");
        contact.setId(123L);
        contact.setName("Name");
        contact.setClientPlatform("Client Platform");
        contact.setDateCreated(1L);
        contact.setStatus(ContactStatus.TEMP);
        contact.setClientId("42");
        return contact;

    }


}

