package de.superchat.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {

    private Long id;
    private String name;
    private String lastName;
    private String email;
    private Long dateCreated;

    public String getFullName() {
        return String.format("%s %s", name, lastName);
    }
}
