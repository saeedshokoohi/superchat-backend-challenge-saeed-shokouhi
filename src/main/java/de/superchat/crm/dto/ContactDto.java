package de.superchat.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {


    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String lastName;
    @Email
    @NotNull
    private String email;
    private Long dateCreated;

    public String getFullName() {
        return String.format("%s %s", name, lastName);
    }
}
