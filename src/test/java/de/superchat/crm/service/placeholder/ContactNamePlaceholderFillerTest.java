package de.superchat.crm.service.placeholder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.superchat.crm.exception.PlaceholderHandlingException;
import org.junit.jupiter.api.Test;

class ContactNamePlaceholderFillerTest {


    @Test
    void testFillPlaceholder() throws PlaceholderHandlingException {

        //given
        String text="Hello ${contactname} . is ${contactname} your name?";
        String contactName="Saeed";


        //when
        String filledText=new ContactNamePlaceholderFiller(contactName).fillPlaceholder(text);

        //then
        String expected="Hello Saeed . is Saeed your name?";
        assertEquals(expected,filledText);


    }
}

