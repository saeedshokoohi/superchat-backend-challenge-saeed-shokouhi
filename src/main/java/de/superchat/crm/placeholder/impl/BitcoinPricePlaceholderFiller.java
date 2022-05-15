package de.superchat.crm.placeholder.impl;

import de.superchat.crm.exception.PlaceholderHandlingException;
import de.superchat.crm.placeholder.MessageContext;
import de.superchat.crm.placeholder.PlaceholderFiller;
import org.springframework.web.client.RestClientException;

public class BitcoinPricePlaceholderFiller implements PlaceholderFiller {

    private static final String PLACE_HOLDER = "${bitcoinprice}";
    private final BitcoinPriceService bitcoinPriceService;

    public BitcoinPricePlaceholderFiller(BitcoinPriceService bitcoinPriceService) {
        this.bitcoinPriceService = bitcoinPriceService;
    }

    @Override
    public String fillPlaceholder(MessageContext context,String originalMessage) throws PlaceholderHandlingException {

        if (originalMessage == null || !originalMessage.contains(PLACE_HOLDER)) return originalMessage;
        try {
            return originalMessage.replace(PLACE_HOLDER, String.format("%1$,.2f$", this.bitcoinPriceService.getCurrentBitcoinPrice()));

        } catch (RestClientException ex) {
            throw new PlaceholderHandlingException("Error on fetching bitcoin price \n" + ex.getMessage());
        }

    }


}
