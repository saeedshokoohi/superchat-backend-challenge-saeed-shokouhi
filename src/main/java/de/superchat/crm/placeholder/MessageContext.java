package de.superchat.crm.placeholder;

import de.superchat.crm.dto.ContactDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageContext {
    private ContactDto contact;
}
