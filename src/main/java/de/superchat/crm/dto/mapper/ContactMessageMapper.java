package de.superchat.crm.dto.mapper;

import de.superchat.crm.dto.ContactMessageDto;
import de.superchat.crm.entity.ContactMessage;
import de.superchat.crm.util.DateTimeUtil;

public class ContactMessageMapper {

    private ContactMessageMapper() {
    }


    /**
     * Mapping ContantMessage to ContactMessageDto
     * @param contactMessage
     * @return
     */
    public static ContactMessageDto toDto(ContactMessage contactMessage) {
        if (contactMessage == null) return null;
        ContactMessageDto contactMessageDto = new ContactMessageDto();
        contactMessageDto.setId(contactMessage.getId());
        contactMessageDto.setContact(ContactMapper.toBasicDto(contactMessage.getContact()));
        contactMessageDto.setMessage(contactMessage.getMessageContent()!=null?contactMessage.getMessageContent().getContent():"");
        contactMessageDto.setMessagePreview(contactMessage.getMessagePreview());
        contactMessageDto.setStatus(contactMessage.getMessageStatus());
        contactMessageDto.setMessageSource(contactMessage.getMessageSource());
        contactMessageDto.setDateCreated(DateTimeUtil.fromEpochInSecond(contactMessage.getDateCreated()).toString());
        contactMessageDto.setDirection(contactMessage.getDirection());
        return contactMessageDto;
    }


}
