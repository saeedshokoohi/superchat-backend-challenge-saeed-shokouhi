package de.superchat.crm.dto.mapper;

import de.superchat.crm.dto.ContactDto;
import de.superchat.crm.dto.MessageContentDto;
import de.superchat.crm.entity.Contact;
import de.superchat.crm.entity.MessageContent;

import java.util.ArrayList;
import java.util.List;

public class MessageContentMapper {

    private MessageContentMapper() {
    }

    /**
     * mapping ContactDto to Contact
     */
    public static MessageContent toEntity(MessageContentDto messageContentDto)
    {
        return messageContentDto==null ? null
                : new MessageContent(messageContentDto.getContentType(),messageContentDto.getContent());
    }

    /**
     * mapping Contact to ContactDto
     */
    public static MessageContentDto toDto(MessageContent messageContent)
    {
        return messageContent==null ? null
                : new MessageContentDto(messageContent.getContentType(),messageContent.getContent());
    }



}
