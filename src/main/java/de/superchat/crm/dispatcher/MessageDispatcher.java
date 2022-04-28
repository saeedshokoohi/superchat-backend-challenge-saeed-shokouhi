package de.superchat.crm.dispatcher;

import de.superchat.crm.dto.MessageDto;
import de.superchat.crm.exception.SendingExternalMessageException;

public interface MessageDispatcher {

    boolean sendMessage(MessageDto message) throws SendingExternalMessageException;
}
