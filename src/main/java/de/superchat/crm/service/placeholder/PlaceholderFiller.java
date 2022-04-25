package de.superchat.crm.service.placeholder;

import de.superchat.crm.exception.PlaceholderHandlingException;

public interface PlaceholderFiller {

    /**
     * This method get the original message and replace placeholders with proper value
     * @param originalMessage
     * @return
     */
    String fillPlaceholder(String originalMessage) throws PlaceholderHandlingException;

}
