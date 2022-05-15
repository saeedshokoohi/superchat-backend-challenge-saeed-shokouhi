package de.superchat.crm.service;

import de.superchat.crm.placeholder.impl.BitcoinPricePlaceholderFiller;
import de.superchat.crm.placeholder.impl.BitcoinPriceService;
import de.superchat.crm.placeholder.impl.ContactNamePlaceholderFiller;

import org.springframework.stereotype.Service;

@Service
public class SetupService {
    final ContactMessageService contactMessageService;
    final BitcoinPriceService bitcoinPriceService;


    public SetupService(ContactMessageService contactMessageService, BitcoinPriceService bitcoinPriceService)
    {
        this.contactMessageService = contactMessageService;
        this.bitcoinPriceService = bitcoinPriceService;
        registerPlaceHolderFillers();

    }

    public void registerPlaceHolderFillers()
    {
        contactMessageService.registerPlaceHolderFiller(new ContactNamePlaceholderFiller(),new BitcoinPricePlaceholderFiller(bitcoinPriceService));
    }


}
