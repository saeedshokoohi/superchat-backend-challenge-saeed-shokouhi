package de.superchat.crm.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.superchat.crm.dto.ContactDto;
import de.superchat.crm.dto.ContactListDto;
import de.superchat.crm.exception.InvalidModelException;
import de.superchat.crm.service.ContactService;
import de.superchat.crm.util.DateTimeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class ContactResourceTest {


    @MockBean
    private ContactService contactService;

    @Autowired
    private MockMvc mockMvc;




    @Test
    void testContactList() throws Exception {
        //given
        List<ContactDto> contacts=new ArrayList<>();
        contacts.add(new ContactDto(4000l,"Saeed","Shokouhi","saeed@gmail.com",null));
        ContactListDto contactListDto=new ContactListDto(contacts);
        when(contactService.getAll()).thenReturn(contactListDto);

        //then
        String expectedContent="{\"list\":[{\"name\":\"Saeed\",\"lastName\":\"Shokouhi\",\"email\":\"saeed@gmail.com\",\"id\":4000,\"dateCreated\":null,\"fullName\":\"Saeed Shokouhi\"}]}";
        this.mockMvc
                .perform(get("/api/contact/list")) // perform a request that can be chained
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent));
    }

    @Test
    void testCreateContact() throws Exception {
        //given
        ContactDto contact= new ContactDto(4000l,"Saeed","Shokouhi","saeed@gmail.com", DateTimeUtil.now());
        when(contactService.create((ContactDto) any())).thenReturn(contact);

        //then

        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/contact/create")
                        .content(asJsonString(contact))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateCreated").exists());

    }
    @Test
    void testCreateContactWhenInvalidModel() throws Exception {
        //given
        ContactDto contact= new ContactDto(4000l,"Saeed","Shokouhi","saeed@gmailcom", DateTimeUtil.now());
        when(contactService.create((ContactDto) any())).thenThrow(InvalidModelException.class);

        //then

        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/contact/create")
                        .content(asJsonString(contact))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());


    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

