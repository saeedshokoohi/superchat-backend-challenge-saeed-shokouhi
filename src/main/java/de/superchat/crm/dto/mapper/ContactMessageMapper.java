package de.superchat.crm.dto.mapper;

import de.superchat.crm.dto.BasicMessageDto;
import de.superchat.crm.dto.ContactMessageDto;
import de.superchat.crm.entity.ContactMessage;
import de.superchat.crm.util.DateTimeUtil;

public class ContactMessageMapper {

    private ContactMessageMapper() {
    }


    /**
     * Mapping ContactMessage to ContactMessageDto
     * @param contactMessage
     * @return
     */
    public static ContactMessageDto toDto(ContactMessage contactMessage) {
        if (contactMessage == null) return null;
        ContactMessageDto contactMessageDto = new ContactMessageDto();
        contactMessageDto.setId(contactMessage.getId());
        contactMessageDto.setContact(ContactMapper.toDto(contactMessage.getContact()));
        contactMessageDto.setMessage(contactMessage.getMessageContent()!=null?contactMessage.getMessageContent().getContent():"");
        contactMessageDto.setMessagePreview(contactMessage.getMessagePreview());
        contactMessageDto.setStatus(contactMessage.getMessageStatus());
        contactMessageDto.setDateCreated(DateTimeUtil.fromEpochInSecond(contactMessage.getDateCreated()).toString());
        contactMessageDto.setDirection(contactMessage.getDirection());
        return contactMessageDto;
    }

    /**
     * mapping contactMessage entity to basic Message Dto
     * @param contactMessage
     * @return
     */
    public static BasicMessageDto toBasicMessage(ContactMessage contactMessage) {
       if(contactMessage==null || contactMessage.getMessageContent()==null)return null;
        return new BasicMessageDto(DateTimeUtil.fromEpochInSecond(contactMessage.getDateCreated()),contactMessage.getMessageContent().getContent(),contactMessage.getDirection(),contactMessage.getMessageStatus());

    }
}
