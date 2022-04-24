package de.superchat.crm.dto;

import de.superchat.crm.entity.enums.MessageContentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageContentDto {

    private MessageContentType contentType;
    private String content;

    public MessageContentDto(String content) {
        this.contentType=MessageContentType.TEXT;
        this.content = content;
    }


}
