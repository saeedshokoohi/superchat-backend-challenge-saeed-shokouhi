package de.superchat.crm.dto;

import lombok.Data;

@Data
public class SendMessageDto {

    private long contactId;

    private String message;
}
