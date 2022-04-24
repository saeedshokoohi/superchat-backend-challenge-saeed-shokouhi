package de.superchat.crm.dto.mapper;

import de.superchat.crm.dto.ContactMessageDto;
import de.superchat.crm.dto.ExternalMessageDto;
import de.superchat.crm.entity.ContactMessage;
import de.superchat.crm.entity.enums.MessageDirection;

public class ContactMessageMapper {

    private ContactMessageMapper() {
    }

    /**
     * mapping ExternalMessage to ContactMessageDto
     * @param externalMessageDto
     * @return
     */
    public static ContactMessageDto toDto(ExternalMessageDto externalMessageDto) {
        if (externalMessageDto == null) return null;
        ContactMessageDto contactMessage = new ContactMessageDto();
        contactMessage.setContact(externalMessageDto.getSender());
        contactMessage.setMessageContent(externalMessageDto.getMessage());
        contactMessage.setMessageSource(externalMessageDto.getSource());
        contactMessage.setDirection(MessageDirection.IN);
        return contactMessage;
    }

    /**
     * Mapping ContantMessage to ContactMessageDto
     * @param contactMessage
     * @return
     */
    public static ContactMessageDto toDto(ContactMessage contactMessage) {
        if (contactMessage == null) return null;
        ContactMessageDto contactMessageDto = new ContactMessageDto();
        contactMessageDto.setContact(ContactMapper.toDto(contactMessage.getContact()));
        contactMessageDto.setMessageContent(MessageContentMapper.toDto(contactMessage.getMessageContent()));
        contactMessageDto.setMessagePreview(contactMessage.getMessagePreview());
        contactMessageDto.setMessageStatus(contactMessage.getMessageStatus());
        contactMessageDto.setDirection(contactMessage.getDirection());
        return contactMessageDto;
    }

    /**
     * Mapping contactMessageDto to ContactMessage
     * @param contactMessageDto
     * @return
     */
    public static ContactMessage toEntity(ContactMessageDto contactMessageDto) {
        if (contactMessageDto == null) return null;
        ContactMessage contactMessage = new ContactMessage();
        contactMessage.setContact(ContactMapper.toEntity(contactMessageDto.getContact()));
        contactMessage.setMessageContent(MessageContentMapper.toEntity(contactMessageDto.getMessageContent()));
        contactMessage.setMessagePreview(contactMessageDto.getMessagePreview());
        contactMessage.setMessageStatus(contactMessageDto.getMessageStatus());
        contactMessage.setDirection(contactMessageDto.getDirection());
        return contactMessage;
    }
}
