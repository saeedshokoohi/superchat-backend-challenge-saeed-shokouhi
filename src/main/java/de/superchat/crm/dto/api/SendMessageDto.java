package de.superchat.crm.dto.api;

import lombok.Data;


@Data
public class SendMessageDto {

    private long contactId;

    private String message;
}
