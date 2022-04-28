package de.superchat.crm.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.superchat.crm.dto.*;
import de.superchat.crm.dto.api.SendMessageDto;
import de.superchat.crm.exception.InvalidModelException;
import de.superchat.crm.service.ContactMessageService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ContactMessageResourceTest {


    @MockBean
    private ContactMessageService contactMessageService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void testMessageListByContactId() throws Exception {
        //given
        long contactId=1122l;
        String messageStr="HELLO";
        ContactDto contact=new ContactDto().setId(contactId);
        List<BasicMessageDto> messages=new ArrayList<>();
        BasicMessageDto message=new BasicMessageDto();
        message.setMessage(messageStr);
        messages.add(message);
        ConversationDto conversation=new ConversationDto(contact,messages);
        when(contactMessageService.conversationByContactId(anyLong())).thenReturn(conversation);


        //then
        this.mockMvc
                .perform(get("/api/message/conversation-by-contact-id/"+contactId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messageCount").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contact.id").value(contactId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0].message").value(messageStr));

    }

    @Test
    void testSendMessage() throws Exception, InvalidModelException {
        //given
        String message = "Hello";
        Long contactId = 1l;
        SendMessageDto sendMessageDto = new SendMessageDto();
        sendMessageDto.setMessage(message);
        sendMessageDto.setContactId(contactId);

        ContactMessageDto contactMessage = new ContactMessageDto();
        contactMessage.setId(2l);


        when(contactMessageService.sendTextMessage(any())).thenReturn(contactMessage);

        //then

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/message/send-message")
                        .content(asJsonString(sendMessageDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2l));


    }

    @Test
    void testCreateContactWhenInvalidModel() throws Exception, InvalidModelException {
        //given
        String message = "Hello";
        Long contactId = 1l;
        SendMessageDto sendMessageDto = new SendMessageDto();
        sendMessageDto.setMessage(message);
        sendMessageDto.setContactId(contactId);

        when(contactMessageService.sendTextMessage((SendMessageDto) any())).thenThrow(InvalidModelException.class);

        //then

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/contact/create")
                        .content(asJsonString(sendMessageDto))
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

