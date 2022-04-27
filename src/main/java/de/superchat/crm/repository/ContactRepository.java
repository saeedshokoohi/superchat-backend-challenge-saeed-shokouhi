package de.superchat.crm.repository;

import de.superchat.crm.entity.Contact;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ContactRepository extends CrudRepository<Contact, Long> {

    Optional<Contact> findByEmail(String email);

    Optional<Contact> findByClientIdAndClientPlatform(String clientId, String clientPlatform);
}
