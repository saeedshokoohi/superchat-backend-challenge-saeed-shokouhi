package de.superchat.crm.placeholder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.superchat.crm.dto.ContactDto;
import de.superchat.crm.exception.PlaceholderHandlingException;
import de.superchat.crm.placeholder.impl.ContactNamePlaceholderFiller;
import org.junit.jupiter.api.Test;

class ContactNamePlaceholderFillerTest {



    @Test
    void testFillPlaceholder() throws PlaceholderHandlingException {

        //given
        String text = "Hello ${contactname} . is ${contactname} your name?";
        String contactName = "Saeed";
        MessageContext messageContext=new MessageContext(new ContactDto().setName(contactName).setLastName(""));

        //when
        String filledText = new ContactNamePlaceholderFiller().fillPlaceholder(messageContext,text);

        //then
        String expected = "Hello Saeed  . is Saeed  your name?";
        assertEquals(expected, filledText);


    }




}

