package de.superchat.crm.placeholder;

import de.superchat.crm.exception.PlaceholderHandlingException;

/**
 * a general interface for placeholder implementations
 */
public interface PlaceholderFiller {

    /**
     * This method get the original message and replace placeholders with proper value
     * @param originalMessage
     * @return
     */
    String fillPlaceholder(MessageContext context,String originalMessage) throws PlaceholderHandlingException;

}
