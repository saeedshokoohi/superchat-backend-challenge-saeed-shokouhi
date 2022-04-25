package de.superchat.crm.dto;

import de.superchat.crm.entity.enums.MessageDirection;
import de.superchat.crm.entity.enums.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactMessageDto {

    private Long id;

    private MessageDirection direction;

    private Long dateCreated;

    private MessageStatus messageStatus;

    private MessageContentDto messageContent;

    private ContactDto contact;

    private String messagePreview;

    private String messageSource;



}
