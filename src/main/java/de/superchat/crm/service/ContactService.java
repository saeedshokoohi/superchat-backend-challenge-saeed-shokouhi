package de.superchat.crm.service;

import de.superchat.crm.dto.ContactDto;
import de.superchat.crm.dto.ContactListDto;
import de.superchat.crm.dto.mapper.ContactMapper;
import de.superchat.crm.entity.Contact;
import de.superchat.crm.repository.ContactRepository;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    /**
     * Persisting new contact
     * @param contactDto contactDto to create
     * @return
     */
    public ContactDto create(ContactDto contactDto)
    {
        Contact contact= ContactMapper.toEntity(contactDto);
        return ContactMapper.toDto(contactRepository.save(contact));
    }

    /**
     * fetching all available Contacts from DataBase
     * @return ContactListDto which contains contact list
     */
    public ContactListDto getAll() {
      return  ContactMapper.toListDto(contactRepository.findAll());

    }
}
