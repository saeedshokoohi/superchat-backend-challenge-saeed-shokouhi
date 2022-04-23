package de.superchat.crm.entity;

import de.superchat.crm.entity.enums.MessageDirection;
import de.superchat.crm.entity.enums.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contact_message",schema = "public" )
public class ContactMessage {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "dir", nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageDirection direction;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageStatus messageStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "msg_content", referencedColumnName = "id")
    private MessageContent messageContent;

    @Column(name = "msg_preview")
    private String messagePreview;

    @Column(name="msg_source")
    private String messageSource;







}
