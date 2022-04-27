package de.superchat.crm.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * This DTO is for a specific contact conversation messages
 */
public class ConversationDto {

    @Getter
    private int messageCount;
    @Getter @Setter
    private ContactDto contact;
    @Getter @Setter
    private List<BasicMessageDto> messages;

    public ConversationDto(ContactDto contactDto,List<BasicMessageDto> messages)
    {
       this.contact =contactDto;
       this.messages=messages!=null?messages:new ArrayList<>();
       this.messageCount=messages!=null? messages.size() : 0;
    }


}
