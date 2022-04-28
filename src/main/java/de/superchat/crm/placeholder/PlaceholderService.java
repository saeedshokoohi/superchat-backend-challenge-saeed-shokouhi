package de.superchat.crm.placeholder;

import de.superchat.crm.entity.ContactMessage;
import de.superchat.crm.exception.PlaceholderHandlingException;
import de.superchat.crm.placeholder.impl.BitcoinPricePlaceholderFiller;
import de.superchat.crm.placeholder.impl.BitcoinPriceService;
import de.superchat.crm.placeholder.impl.ContactNamePlaceholderFiller;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaceholderService {

    private final BitcoinPriceService bitcoinPriceService;

    public PlaceholderService(BitcoinPriceService bitcoinPriceService) {
        this.bitcoinPriceService = bitcoinPriceService;
    }


    /**
     * applying the placeholder logic on the out messages
     * @param contactMessage
     * @throws PlaceholderHandlingException
     */
    public void applyPlaceholders(ContactMessage contactMessage) throws PlaceholderHandlingException {

        if (contactMessage == null || contactMessage.getMessageContent() == null || contactMessage.getContact() == null)
            return ;

        //getting registered placeholders
        List<PlaceholderFiller> registeredPlaceholderFillers = getRegisteredPlaceholders(contactMessage);

        //applying placeholder fillers on message
        String filledMessage = contactMessage.getMessageContent().getContent();
        filledMessage = applyRegisteredPlaceHolderFillers(registeredPlaceholderFillers, filledMessage);

        //setting on given
        contactMessage.getMessageContent().setContent(filledMessage);


    }

    /**
     *  returns a list of registered placeholders
     *  this list can be obtained base on configs or annotations
     * @param contactMessage
     * @return
     */
    private List<PlaceholderFiller> getRegisteredPlaceholders(ContactMessage contactMessage) {
        List<PlaceholderFiller> registeredPlaceholderFillers = new ArrayList<>();
        registeredPlaceholderFillers.add(new ContactNamePlaceholderFiller(contactMessage.getContact().getFullName()));
        registeredPlaceholderFillers.add(new BitcoinPricePlaceholderFiller(bitcoinPriceService));
        return registeredPlaceholderFillers;
    }

    /**
     * applying all registered placeholder fillers on given text message
     * @param registeredPlaceholderFillers which need to be applied
     * @param filledMessage the message which placehodlers may be applied
     * @return
     * @throws PlaceholderHandlingException
     */
    private String applyRegisteredPlaceHolderFillers(List<PlaceholderFiller> registeredPlaceholderFillers, String filledMessage) throws PlaceholderHandlingException {
        for (PlaceholderFiller placeholderFiller : registeredPlaceholderFillers) {
            filledMessage = placeholderFiller.fillPlaceholder(filledMessage);
        }
        return filledMessage;
    }
}
