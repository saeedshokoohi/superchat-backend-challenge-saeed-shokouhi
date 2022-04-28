package de.superchat.crm.dto.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import de.superchat.crm.dto.ContactDto;
import de.superchat.crm.dto.ContactListDto;
import de.superchat.crm.entity.Contact;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

class ContactMapperTest {

    @Test
    void testToEntityWhenNull() {
        assertNull(ContactMapper.toEntity(null));
    }
    @Test
    void testToEntity() {
        //given
         Long id=1312L;
         String name="saeed";
         String lastName="sh";
         String email="saeed.sh@gmai.com";
        String clientId="saeed.sh@gmai.com";
        String platform="email";

        Long dateCreated= LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
         //when
        Contact actualToEntityResult = ContactMapper.toEntity(new ContactDto().setId(id).setEmail(email).setDateCreated(dateCreated)
                .setClientId(clientId).setName(name).setLastName(lastName).setClientPlatform(platform));
        //then
        assertEquals(id,actualToEntityResult.getId());
        assertEquals(name,actualToEntityResult.getName());
        assertEquals(lastName,actualToEntityResult.getLastName());
        assertEquals(email,actualToEntityResult.getEmail());
        assertEquals(dateCreated,actualToEntityResult.getDateCreated());
        assertEquals(clientId,actualToEntityResult.getClientId());
        assertEquals(platform,actualToEntityResult.getClientPlatform());
    }



    @Test
    void testToDto() {
        //given
        Long id=1312L;
        String name="saeed";
        String lastName="sh";
        String email="saeed.sh@gmai.com";
        String clientId="saeed.sh@gmai.com";
        String platform="email";

        Long dateCreated= LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        //when
        ContactDto actualToDto = ContactMapper.toDto(new Contact(id,clientId,platform,name,lastName,email,dateCreated));
        //then
        assertEquals(id,actualToDto.getId());
        assertEquals(name,actualToDto.getName());
        assertEquals(lastName,actualToDto.getLastName());
        assertEquals(email,actualToDto.getEmail());
        assertEquals(dateCreated,actualToDto.getDateCreated());
        assertEquals(clientId,actualToDto.getClientId());
        assertEquals(platform,actualToDto.getClientPlatform());
    }



    @Test
    void testToListDtoWhenGivenListIsNull() {
        //given

        Iterable list=null;
        //when
        ContactListDto contactListDto=ContactMapper.toListDto(list);
        //then
        assertNotNull(contactListDto);
        assertNotNull(contactListDto.getList());
        assertEquals(0,contactListDto.getList().size());
    }

    @Test
    void testToListDto() {
        //given
        Long dateCreated= LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        Contact contact1=new Contact(123l,"cl1","p","N1","Ln1","E1@a.d",dateCreated);
        Contact contact2=new Contact(124l,"cl2","p","N2","Ln2","E2@a.d",dateCreated);
        Iterable<Contact> list= List.of(contact1,contact2);
        //when
        ContactListDto contactListDto=ContactMapper.toListDto(list);
        //then
        assertNotNull(contactListDto);
        assertNotNull(contactListDto.getList());
        assertEquals(2,contactListDto.getList().size());
        assertEquals(ContactMapper.toDto(contact1),contactListDto.getList().get(0));
        assertEquals(ContactMapper.toDto(contact2),contactListDto.getList().get(1));
    }
}

