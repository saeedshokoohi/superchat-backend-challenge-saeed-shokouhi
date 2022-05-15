package de.superchat.crm.placeholder;

import de.superchat.crm.exception.PlaceholderHandlingException;
import de.superchat.crm.placeholder.impl.BitcoinPricePlaceholderFiller;
import de.superchat.crm.placeholder.impl.BitcoinPriceService;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClientException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BitcoinPricePlaceholderFillerTest {


    @Test
    void testFillPlaceholder() throws PlaceholderHandlingException {

        //given
        String text = "current bitcoin price is ${bitcoinprice}";
        BitcoinPriceService bitcoinPriceService = mock(BitcoinPriceService.class);
        when(bitcoinPriceService.getCurrentBitcoinPrice()).thenReturn(40000d);
        MessageContext messageContext=new MessageContext();

        //when
        String filledText = new BitcoinPricePlaceholderFiller(bitcoinPriceService).fillPlaceholder(messageContext,text);

        //then
        String expected = "current bitcoin price is 40,000.00$";
        assertEquals(expected, filledText);


    }

    @Test
    void testFillPlaceholderWhenException() throws PlaceholderHandlingException {
        //given
        String text = "current bitcoin price is ${bitcoinprice}";
        BitcoinPriceService bitcoinPriceService = mock(BitcoinPriceService.class);
        when(bitcoinPriceService.getCurrentBitcoinPrice()).thenThrow(new RestClientException(""));
        MessageContext messageContext=new MessageContext();

        //then
        assertThrows(PlaceholderHandlingException.class, () -> new BitcoinPricePlaceholderFiller(bitcoinPriceService).fillPlaceholder(messageContext,text));


    }
}

