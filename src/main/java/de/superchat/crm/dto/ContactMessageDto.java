package de.superchat.crm.dto;

import de.superchat.crm.entity.enums.MessageDirection;
import de.superchat.crm.entity.enums.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactMessageDto {

    private Long id;

    private MessageDirection direction;

    private String dateCreated;

    private MessageStatus status;

    private String message;

    private ContactDto contact;

    private String messagePreview;

    private String messageSource;



}
