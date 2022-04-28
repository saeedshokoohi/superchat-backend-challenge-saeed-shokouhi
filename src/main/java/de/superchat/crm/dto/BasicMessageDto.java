package de.superchat.crm.dto;

import de.superchat.crm.entity.enums.MessageDirection;
import de.superchat.crm.entity.enums.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * This
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicMessageDto {
    private Long id;
    private LocalDateTime dateCreated;
    private String message;
    private MessageDirection dir;
    private MessageStatus status;

}
