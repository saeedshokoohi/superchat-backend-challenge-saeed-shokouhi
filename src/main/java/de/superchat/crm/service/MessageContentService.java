package de.superchat.crm.service;

import de.superchat.crm.client.BitcoinPriceService;
import de.superchat.crm.entity.ContactMessage;
import de.superchat.crm.exception.PlaceholderHandlingException;
import de.superchat.crm.service.placeholder.BitcoinPricePlaceholderFiller;
import de.superchat.crm.service.placeholder.ContactNamePlaceholderFiller;
import de.superchat.crm.service.placeholder.PlaceholderFiller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageContentService {

    final BitcoinPriceService bitcoinPriceService;

    public MessageContentService(BitcoinPriceService bitcoinPriceService) {
        this.bitcoinPriceService = bitcoinPriceService;
    }


    public void applyPlaceholders(ContactMessage contactMessage) throws PlaceholderHandlingException {

        if (contactMessage == null || contactMessage.getMessageContent() == null || contactMessage.getContact() == null)
            return ;

        //registering placeholder fillers
        List<PlaceholderFiller> registeredPlaceholderFillers = new ArrayList<>();
        registeredPlaceholderFillers.add(new ContactNamePlaceholderFiller(contactMessage.getContact().getFullName()));
        registeredPlaceholderFillers.add(new BitcoinPricePlaceholderFiller(bitcoinPriceService));

        //applying placeholder fillers on message
        String filledMessage = contactMessage.getMessageContent().getContent();
        filledMessage = applyRegisteredPlaceHolderFillers(registeredPlaceholderFillers, filledMessage);

        //setting on given
        contactMessage.getMessageContent().setContent(filledMessage);


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


    public String makeTextMessagePreview(String message) {
        return message==null?"": limitedMessageLength(message,20);
    }

    private String limitedMessageLength(String message, int maxLength)
    {
      return  message.length()>maxLength? message.substring(0,maxLength-1)+" ...": message;
    }
}
