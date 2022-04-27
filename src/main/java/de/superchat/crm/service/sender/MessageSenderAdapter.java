package de.superchat.crm.service.sender;

import de.superchat.crm.dto.MessageDto;
import de.superchat.crm.exception.SendingExternalMessageException;

public interface MessageSenderAdapter {

    boolean sendMessage(MessageDto message) throws SendingExternalMessageException;
}
