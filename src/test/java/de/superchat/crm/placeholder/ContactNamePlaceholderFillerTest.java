package de.superchat.crm.placeholder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.superchat.crm.exception.PlaceholderHandlingException;
import de.superchat.crm.placeholder.impl.ContactNamePlaceholderFiller;
import org.junit.jupiter.api.Test;

class ContactNamePlaceholderFillerTest {


    @Test
    void testConstructor() {
        // TODO: This test is incomplete.
        //   Reason: R002 Missing observers.
        //   Diffblue Cover was unable to create an assertion.
        //   Add getters for the following fields or make them package-private:
        //     ContactNamePlaceholderFiller.contactName

        new ContactNamePlaceholderFiller("Contact Name");
    }

    @Test
    void testFillPlaceholder() throws PlaceholderHandlingException {

        //given
        String text = "Hello ${contactname} . is ${contactname} your name?";
        String contactName = "Saeed";


        //when
        String filledText = new ContactNamePlaceholderFiller(contactName).fillPlaceholder(text);

        //then
        String expected = "Hello Saeed . is Saeed your name?";
        assertEquals(expected, filledText);


    }

    @Test
    void testFillPlaceholder2() throws PlaceholderHandlingException {
        assertEquals("Original Message",
                (new ContactNamePlaceholderFiller("Contact Name")).fillPlaceholder("Original Message"));
    }

    @Test
    void testFillPlaceholder3() throws PlaceholderHandlingException {
        assertEquals("Original Message", (new ContactNamePlaceholderFiller("42")).fillPlaceholder("Original Message"));
    }

    @Test
    void testFillPlaceholder4() throws PlaceholderHandlingException {
        assertNull((new ContactNamePlaceholderFiller("Contact Name")).fillPlaceholder(null));
    }
}

