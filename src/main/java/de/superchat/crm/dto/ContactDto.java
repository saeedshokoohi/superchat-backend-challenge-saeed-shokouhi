package de.superchat.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto extends BasicContactDto {

    private Long id;
    private Long dateCreated;

    public ContactDto(Long id,String name, String lastName, String email, Long dateCreated) {
        super(name, lastName, email);
        this.id = id;
        this.dateCreated = dateCreated;
    }

    public ContactDto(BasicContactDto contact) {
        super(contact.getName(),contact.getLastName(),contact.getEmail());
    }
}
