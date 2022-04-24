package de.superchat.crm.rest;


import de.superchat.crm.dto.ContactDto;
import de.superchat.crm.dto.ContactListDto;
import de.superchat.crm.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping(path = "api/contact/")
@RestController
public class ContactResource {
    final ContactService contactService;

    public ContactResource(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("create")
    public ResponseEntity<ContactListDto> createContact(@RequestBody @Valid ContactDto contact) {
        return ResponseEntity.ok()
                .body(contactService.getAll());
    }


}
