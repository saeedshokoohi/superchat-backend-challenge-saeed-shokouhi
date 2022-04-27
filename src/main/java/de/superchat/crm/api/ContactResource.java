package de.superchat.crm.api;


import de.superchat.crm.dto.ContactDto;
import de.superchat.crm.dto.ContactListDto;
import de.superchat.crm.exception.InvalidModelException;
import de.superchat.crm.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(path = "api/contact/")
@RestController
public class ContactResource {
    final ContactService contactService;


    public ContactResource(ContactService contactService) {
        this.contactService = contactService;
    }

    /**
     * Adding new contact
     * @param contact to save
     * @return saved contact
     */

    @PostMapping("create")
    public ResponseEntity<ContactDto> createContact(@RequestBody @Valid ContactDto contact) throws InvalidModelException {
            return ResponseEntity.ok()
                    .body(contactService.createConfirmedContact(contact));
    }

    /**
     * return all persisted list of contacts
     * @return ContactListDto which has the list
     */
    @GetMapping("list")
    public ResponseEntity<ContactListDto> contactList() {
        return ResponseEntity.ok()
                .body(contactService.getAll());
    }

    /**
     * exception handler for InvalidModelException
     * @param ex is the raised exception
     * @return error in string
     */
    @ExceptionHandler({ InvalidModelException.class })
    public ResponseEntity<String> handleException(InvalidModelException ex) {
        return ResponseEntity.badRequest().body(ex!=null? ex.toString():"");
    }

}
