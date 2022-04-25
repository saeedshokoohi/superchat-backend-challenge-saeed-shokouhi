package de.superchat.crm.service.placeholder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import de.superchat.crm.client.BitcoinPriceService;
import de.superchat.crm.exception.PlaceholderHandlingException;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClientException;

class BitcoinPricePlaceholderFillerTest {



    @Test
    void testFillPlaceholder() throws PlaceholderHandlingException {

        //given
        String text="current bitcoin price is ${bitcoinprice}" ;
        BitcoinPriceService bitcoinPriceService=mock(BitcoinPriceService.class);
       when(bitcoinPriceService.getCurrentBitcoinPrice()).thenReturn(40000d);


        //when
        String filledText=new BitcoinPricePlaceholderFiller(bitcoinPriceService).fillPlaceholder(text);

        //then
        String expected="current bitcoin price is 40,000.00$";
        assertEquals(expected,filledText);


    }
    @Test
    void testFillPlaceholderWhenException() throws PlaceholderHandlingException {

        //given
        String text="current bitcoin price is ${bitcoinprice}" ;
        BitcoinPriceService bitcoinPriceService=mock(BitcoinPriceService.class);
        when(bitcoinPriceService.getCurrentBitcoinPrice()).thenThrow(new RestClientException(""));


        //then
        PlaceholderHandlingException exception= assertThrows(PlaceholderHandlingException.class,()-> new BitcoinPricePlaceholderFiller(bitcoinPriceService).fillPlaceholder(text));




    }
}
