package de.superchat.crm.placeholder;

import de.superchat.crm.dto.mapper.ContactMapper;
import de.superchat.crm.entity.ContactMessage;
import de.superchat.crm.exception.PlaceholderHandlingException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PlaceholderService {



    /**
     * applying the placeholder logic on the out messages
     * @param contactMessage related contact message
     * @throws PlaceholderHandlingException
     */
    public void applyPlaceholders(ContactMessage contactMessage, Set<PlaceholderFiller> placeholderFillers) throws PlaceholderHandlingException {

        if (contactMessage == null || contactMessage.getMessageContent() == null || contactMessage.getContact() == null)
            return ;

        //applying placeholder fillers on message
        String filledMessage = contactMessage.getMessageContent().getContent();
        filledMessage = applyRegisteredPlaceHolderFillers(placeholderFillers,contactMessage, filledMessage);

        //setting on given
        contactMessage.getMessageContent().setContent(filledMessage);
    }

    /**
     * applying all registered placeholder fillers on given text message
     * @param registeredPlaceholderFillers which need to be applied
     * @param contactMessage
     * @param filledMessage the message which placeholders may be applied
     * @return
     * @throws PlaceholderHandlingException
     */
    private String applyRegisteredPlaceHolderFillers(Set<PlaceholderFiller> registeredPlaceholderFillers, ContactMessage contactMessage, String filledMessage) throws PlaceholderHandlingException {
        MessageContext messageContext=new MessageContext(ContactMapper.toDto(contactMessage.getContact()));
        for (PlaceholderFiller placeholderFiller : registeredPlaceholderFillers) {
            filledMessage = placeholderFiller.fillPlaceholder(messageContext,filledMessage);
        }
        return filledMessage;
    }
}
