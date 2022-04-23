package de.superchat.crm.repository;

import de.superchat.crm.entity.ContactMessage;
import org.springframework.data.repository.CrudRepository;

public interface ContactMessageRepository extends CrudRepository<ContactMessage,Long> {
}
