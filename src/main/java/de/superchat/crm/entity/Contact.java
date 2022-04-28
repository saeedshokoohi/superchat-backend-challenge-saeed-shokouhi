package de.superchat.crm.entity;

import de.superchat.crm.entity.enums.ContactStatus;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Data
@Entity
@Table(name = "contact",schema = "public" ,uniqueConstraints = { @UniqueConstraint(columnNames = { "client_id", "client_platform" }) } )
public class Contact {

    @Id
    @GeneratedValue(generator ="contact_sequence")
    private Long id;

    //the client Id which is identifiable in the platform
    @Column(name="client_id")
    private String clientId;

    @Column(name="client_platform")
    private String clientPlatform;

    @Column(name = "first_name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "date_created")
    private Long dateCreated;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private ContactStatus status;

    public Contact() {
    }

    public Contact(Long id, String clientId, String clientPlatform, String name, String lastName, String email, Long dateCreated) {
        this.id = id;
        this.clientId = clientId;
        this.clientPlatform = clientPlatform;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.dateCreated = dateCreated;
    }

    public Contact(String clientId, String clientPlatform, String name, String lastName, String email, Long dateCreated) {
        this.clientId = clientId;
        this.clientPlatform = clientPlatform;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.dateCreated = dateCreated;
    }

    public String getFullName() {
        return String.format("%s %s", name, lastName);
    }
}
