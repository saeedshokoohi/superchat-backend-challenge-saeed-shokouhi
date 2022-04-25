package de.superchat.crm.dto;

import lombok.Data;

@Data
public class SendMessageDto {
    //sender email address
    private String receiverEmail;

    private String message;
}
