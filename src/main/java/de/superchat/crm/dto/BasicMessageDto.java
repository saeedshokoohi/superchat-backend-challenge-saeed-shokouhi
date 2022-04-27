package de.superchat.crm.dto;

import de.superchat.crm.entity.enums.MessageDirection;
import de.superchat.crm.entity.enums.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BasicMessageDto {
    private LocalDateTime dateCreated;
    private String message;
    private MessageDirection dir;
    private MessageStatus status;

}
