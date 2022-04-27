package de.superchat.crm.entity;

import de.superchat.crm.entity.enums.MessageContentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "message_content",schema = "public" )
public class MessageContent {

    @Id
    @GeneratedValue(generator = "message_content_sequence")
    private Long id;


    @Column(name="content_type")
    @Enumerated(EnumType.STRING)
    private MessageContentType contentType;

    @Column(name = "content")
    private String content;

    public MessageContent(MessageContentType contentType, String content) {
        this.contentType = contentType;
        this.content = content;
    }

    public MessageContent(String content) {
        this.contentType = MessageContentType.TEXT;
        this.content = content;
    }
}
