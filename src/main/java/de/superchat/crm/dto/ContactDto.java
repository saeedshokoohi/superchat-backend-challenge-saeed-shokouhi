package de.superchat.crm.dto;

import lombok.Getter;

import java.util.Objects;


public class ContactDto {

    @Getter
    private Long id;
    @Getter
    private String name;
    @Getter
    private String lastName;
    @Getter
    private String email;
    @Getter
    private String clientId;
    @Getter
    private String clientPlatform;
    @Getter
    private Long dateCreated;


    //Setters are based on method chain

    public ContactDto setId(Long id) {
        this.id = id;
        return this;
    }

    public ContactDto setName(String name) {
        this.name = name;
        return this;
    }

    public ContactDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public ContactDto setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public ContactDto setClientPlatform(String clientPlatform) {
        this.clientPlatform = clientPlatform;
        return this;
    }

    public ContactDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ContactDto setDateCreated(Long dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public String getFullName() {
        return String.format("%s %s", name, lastName);
    }

    @Override
    public int hashCode() {
        int result = 17;
        int prime=31;
        result = prime * result + Objects.hashCode(id);
        result = prime * result + Objects.hashCode(lastName);
        result = prime * result +Objects.hashCode(name);
        result = prime * result + Objects.hashCode(clientId);
        result = prime * result + Objects.hashCode(clientPlatform);
        result = prime * result + Objects.hashCode(email);
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final ContactDto other = (ContactDto) obj;
        return other.hashCode() == this.hashCode();
    }
}
