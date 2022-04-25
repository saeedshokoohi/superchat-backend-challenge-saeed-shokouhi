package de.superchat.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExternalMessageDto {

    //sender email address
    private String senderEmail;

    //source which is sent from
    //such as Email,Whatsapp,SMS...
    private String source;

    private String message;




}
