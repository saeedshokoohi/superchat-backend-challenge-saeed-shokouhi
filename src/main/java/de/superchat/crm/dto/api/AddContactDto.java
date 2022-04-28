package de.superchat.crm.dto.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddContactDto {

    private String name;
    private String lastName;
    private String email;
    private String clientId;
    private String clientPlatform;

}
