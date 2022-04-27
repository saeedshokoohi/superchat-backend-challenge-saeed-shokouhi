package de.superchat.crm.repository;

import de.superchat.crm.entity.ContactMessage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContactMessageRepository extends CrudRepository<ContactMessage,Long> {

    List<ContactMessage> findAllByContactId(long contactId);

}
