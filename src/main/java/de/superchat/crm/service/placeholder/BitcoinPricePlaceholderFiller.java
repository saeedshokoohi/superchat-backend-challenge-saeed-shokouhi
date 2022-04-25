package de.superchat.crm.service.placeholder;

import de.superchat.crm.client.BitcoinPriceService;
import de.superchat.crm.exception.PlaceholderHandlingException;
import org.springframework.web.client.RestClientException;

public class BitcoinPricePlaceholderFiller implements PlaceholderFiller {

    private static final  String PLACE_HOLDER ="${bitcoinprice}";
    private final BitcoinPriceService bitcoinPriceService;

    public BitcoinPricePlaceholderFiller(BitcoinPriceService bitcoinPriceService) {
        this.bitcoinPriceService = bitcoinPriceService;
    }

    @Override
    public String fillPlaceholder(String originalMessage) throws PlaceholderHandlingException {
        if(originalMessage==null)return null;
        String bitcoinPrice="";
        try {
             bitcoinPrice = String.format("%1$,.2f$", this.bitcoinPriceService.getCurrentBitcoinPrice());

        }catch (RestClientException ex)
        {
            throw new PlaceholderHandlingException("Error on fetching bitcoin price \n"+ex.getMessage());
        }
        return originalMessage.replace(PLACE_HOLDER, bitcoinPrice);
    }


}
