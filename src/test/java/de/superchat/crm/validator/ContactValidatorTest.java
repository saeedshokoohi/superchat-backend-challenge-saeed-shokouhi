package de.superchat.crm.validator;

import de.superchat.crm.dto.ContactDto;
import de.superchat.crm.exception.InvalidModelException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContactValidatorTest {
    @Test
    void ValidateContactTestWhenIsValid()
    {
        //given
        ContactDto contactDto=new ContactDto(122323l,"Name","Last Name","ValidEmail@dss.ddd",null);

        //then
        assertDoesNotThrow(()->{ContactValidator.validateContact(contactDto);});
    }
    @Test
    void ValidateContactTestWhenNameIsNotValid()
    {
        //given
        ContactDto contactDto=new ContactDto(122323l,null,"Last Name","ValidEmail@dss.ddd",null);
        //when
        InvalidModelException exceptionThatWasThrown =assertThrows(InvalidModelException.class, () -> ContactValidator.validateContact(contactDto));

        //then
        assertTrue( Arrays.stream(exceptionThatWasThrown.getValidationMessages()).anyMatch(m->ContactValidator.NAME_IS_REQUIRED.equals(m)));

    }
    @Test
    void ValidateContactTestWhenLastNameIsNotValid()
    {
        //given
        ContactDto contactDto=new ContactDto(122323l,null,null,"ValidEmail@dss.ddd",null);
        //when
        InvalidModelException exceptionThatWasThrown =assertThrows(InvalidModelException.class, () -> ContactValidator.validateContact(contactDto));

        //then
        assertTrue( Arrays.stream(exceptionThatWasThrown.getValidationMessages()).anyMatch(m->ContactValidator.LAST_NAME_IS_REQUIRED.equals(m)));

    }
    @Test
    void ValidateContactTestWhenEmailIsNotValid()
    {
        //given
        ContactDto contactDto=new ContactDto(122323l,null,null,"ValidEmail@dsddd",null);
        //when
        InvalidModelException exceptionThatWasThrown =assertThrows(InvalidModelException.class, () -> ContactValidator.validateContact(contactDto));

        //then
        assertTrue( Arrays.stream(exceptionThatWasThrown.getValidationMessages()).anyMatch(m->ContactValidator.EMAIL_IS_NOT_A_VALID_EMAIL_ADDRESS.equals(m)));

    }
}

