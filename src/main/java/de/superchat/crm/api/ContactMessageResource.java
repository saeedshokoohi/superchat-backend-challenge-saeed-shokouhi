package de.superchat.crm.api;


import de.superchat.crm.dto.ContactMessageDto;
import de.superchat.crm.dto.ContactMessageListDto;
import de.superchat.crm.dto.SendMessageDto;
import de.superchat.crm.exception.InvalidModelException;
import de.superchat.crm.exception.PlaceholderHandlingException;
import de.superchat.crm.service.ContactMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "api/message/")
@RestController
public class ContactMessageResource {

    final
    ContactMessageService contactMessageService;

    public ContactMessageResource(ContactMessageService contactMessageService) {
        this.contactMessageService = contactMessageService;
    }

    /**
     * rest api for sending message
     * @param sendMessageDto
     * @return saved contact message details
     * @throws InvalidModelException if sendMessage object is not valid
     * @throws PlaceholderHandlingException if exception on handling the placeholders values
     */
    @PostMapping("send")
    public ResponseEntity<ContactMessageDto> sendMessage(SendMessageDto sendMessageDto) throws InvalidModelException, PlaceholderHandlingException {

       return ResponseEntity.ok(
        contactMessageService.sendTextMessage(sendMessageDto.getReceiverEmail(), sendMessageDto.getMessage()));

    }

    @GetMapping("list/{email}")
    public ResponseEntity<ContactMessageListDto> messageListByEmail(@PathVariable("email") String email)
    {
        return ResponseEntity.ok(
                contactMessageService.messageListByEmail(email)
        );
    }


    /**
     * exception handler for InvalidModelException
     * @param ex is the raised exception
     * @return error in string
     */
    @ExceptionHandler({ InvalidModelException.class,PlaceholderHandlingException.class })
    public ResponseEntity<String> handleException(InvalidModelException ex) {
        return ResponseEntity.badRequest().body(ex!=null? ex.toString():"");
    }

}
