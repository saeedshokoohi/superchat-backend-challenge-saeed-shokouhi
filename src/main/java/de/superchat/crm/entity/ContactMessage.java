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
    @GeneratedValue(generator = "contact_message_sequence")
    private Long id;

    //define which it is Incoming or Outgoing message
    @Column(name = "dir", nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageDirection direction;

    @Column(name = "date_created")
    private Long dateCreated;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageStatus messageStatus;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "msg_content", referencedColumnName = "id")
    private MessageContent messageContent;

    @ManyToOne
    @JoinColumn(name = "contact", referencedColumnName = "id")
    private Contact contact;

    @Column(name = "msg_preview")
    private String messagePreview;

}
