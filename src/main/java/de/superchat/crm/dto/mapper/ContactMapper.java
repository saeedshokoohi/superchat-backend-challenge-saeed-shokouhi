package de.superchat.crm.dto.mapper;

import de.superchat.crm.dto.ContactDto;
import de.superchat.crm.dto.ContactListDto;
import de.superchat.crm.dto.api.AddContactDto;
import de.superchat.crm.entity.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactMapper {

    private ContactMapper() {
    }

    /**
     * mapping ContactDto to Contact
     */
    public static Contact toEntity(ContactDto contactDto)
    {
        return contactDto==null ? null
                : new Contact(contactDto.getId(), contactDto.getClientId(), contactDto.getClientPlatform(), contactDto.getName(), contactDto.getLastName(),contactDto.getEmail(),contactDto.getDateCreated());
    }

    /**
     * mapping Contact to ContactDto
     */
    public static ContactDto toDto(Contact contact)
    {
        return contact==null ? null
                : new ContactDto()
                .setId(contact.getId())
                .setClientId(contact.getClientId())
                .setClientPlatform(contact.getClientPlatform())
                .setEmail(contact.getEmail())
                .setLastName(contact.getLastName())
                .setName(contact.getName())
                .setDateCreated(contact.getDateCreated());

    }

    public static ContactDto toDto(AddContactDto contact)
    {
        return contact==null ? null
                : new ContactDto()
                .setClientId(contact.getClientId())
                .setClientPlatform(contact.getClientPlatform())
                .setEmail(contact.getEmail())
                .setLastName(contact.getLastName())
                .setName(contact.getName());
    }


    /**
     * converting Iterable of Entity to ContactListDto
     * @param list of contacts
     */
    public static ContactListDto toListDto(Iterable<Contact> list) {
        List<ContactDto> contactDtoList=new ArrayList<>();
        if(list!=null) {
            list.forEach(contact -> contactDtoList.add(toDto(contact)));
        }
        return new ContactListDto(contactDtoList);
    }
}
