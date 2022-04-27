package de.superchat.crm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.superchat.crm.dto.ContactDto;
import de.superchat.crm.dto.mapper.ContactMapper;
import de.superchat.crm.entity.Contact;
import de.superchat.crm.exception.InvalidModelException;
import de.superchat.crm.repository.ContactRepository;
import de.superchat.crm.util.DateTimeUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ContactService.class})
@ExtendWith(SpringExtension.class)
class ContactServiceTest {
    @MockBean
    private ContactRepository contactRepository;

    @Autowired
    private ContactService contactService;



    @Test
    void testCreate() throws InvalidModelException {
        //given
        Contact contact = new Contact(121l,"we","wer","Saeed","Shokouhi","saeed@gmail.com", DateTimeUtil.now());
        ContactDto contactDto = ContactMapper.toDto(contact);
        when(this.contactRepository.save((Contact) any())).thenReturn(contact);

        //when
        ContactDto actualCreateResult = this.contactService.createConfirmedContact(contactDto);

        //then
        assertEquals(contactDto.getEmail(),actualCreateResult.getEmail());
        assertEquals(contactDto.getName(),actualCreateResult.getName());
        assertEquals(contactDto.getLastName(),actualCreateResult.getLastName());
        verify(this.contactRepository).save((Contact) any());

    }



    @Test
    void testGetAll() {
        //given
        Iterable<Contact> iterable = (Iterable<Contact>) mock(Iterable.class);
        doNothing().when(iterable).forEach((java.util.function.Consumer<? super Contact>) any());

        //then
        when(this.contactRepository.findAll()).thenReturn(iterable);
        assertEquals("ContactListDto(list=[])", this.contactService.getAll().toString());
        verify(this.contactRepository).findAll();
        verify(iterable).forEach((java.util.function.Consumer<? super Contact>) any());
    }
}

