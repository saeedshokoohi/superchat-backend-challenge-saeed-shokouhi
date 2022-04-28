package de.superchat.crm.service;

import de.superchat.crm.dto.ContactDto;
import de.superchat.crm.dto.ContactListDto;
import de.superchat.crm.dto.mapper.ContactMapper;
import de.superchat.crm.entity.Contact;
import de.superchat.crm.entity.enums.ContactStatus;
import de.superchat.crm.exception.InvalidModelException;
import de.superchat.crm.repository.ContactRepository;
import de.superchat.crm.util.DateTimeUtil;
import de.superchat.crm.validator.ContactValidator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContactService {

   private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    /**
     * Persisting new confirmed contact
     * @param contactDto contactDto to create
     * @return
     */
    public ContactDto createConfirmedContact(ContactDto contactDto) throws InvalidModelException {
        ContactValidator.validateContact(contactDto);
        checkClientAndPlatformIsUnique(contactDto.getClientId(),contactDto.getClientPlatform());
        Contact contact= ContactMapper.toEntity(contactDto);
        contact.setDateCreated(DateTimeUtil.now());
        contact.setId(null);
        contact.setStatus(ContactStatus.CONFIRMED);
        return ContactMapper.toDto(contactRepository.save(contact));
    }
    /**
     * creating Temporary contact
     * temporary contact are added automatically and the user can confirm or block them
     * @param contactDto
     * @return
     */
    private Contact createTempContact(ContactDto contactDto) throws InvalidModelException {
        ContactValidator.validateTempContact(contactDto);
        Contact contact=new Contact();
        contact.setClientId(contactDto.getClientId());
        contact.setClientPlatform(contactDto.getClientPlatform());
        contact.setEmail(contactDto.getEmail());
        contact.setName(contactDto.getName()!=null? contact.getName() : contact.getClientId());
        contact.setLastName(contactDto.getLastName());
        contact.setStatus(ContactStatus.TEMP);
        return this.contactRepository.save(contact);
    }

    /**
     * fetching single contact by id
     * @param contactId valid contactId
     * @return
     */
    public Optional<ContactDto> getContactById(long contactId) {
        return contactRepository.findById(contactId).map(ContactMapper::toDto);
    }
    /**
     * fetching all available Contacts from DataBase
     * @return ContactListDto which contains contact list
     */
    public ContactListDto getAll() {
      return  ContactMapper.toListDto(contactRepository.findAll());

    }

    /**
     * this method check the client id and platform
     * if there is any user with the following data it will return the contact
     * otherwise it will create it and return the contact
     * @param contactDto
     * @return
     */
    public Contact getOrCreateContact(ContactDto contactDto) throws InvalidModelException {
       Optional<Contact> contact= this.contactRepository.findByClientIdAndClientPlatform(contactDto.getClientId(),contactDto.getClientPlatform());
       return contact.orElse(this.createTempContact(contactDto));
    }

    private void checkClientAndPlatformIsUnique(String clientId,String platform) throws InvalidModelException {
        Optional<Contact> contact= this.contactRepository.findByClientIdAndClientPlatform(clientId,platform);
         if(contact.isPresent()) throw new InvalidModelException(ContactValidator.CLIENT_ID_PLATFORM_PAIR_ALREADY_EXISTS);
    }


    public Optional<Contact> findById(long contactId) {
        return contactRepository.findById(contactId);
    }
}
