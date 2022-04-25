package de.superchat.crm.webhook;


import de.superchat.crm.dto.ExternalMessageDto;
import de.superchat.crm.exception.InvalidModelException;
import de.superchat.crm.service.WebhookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "webhook/")
@RestController
public class MessageWebhook {

    final WebhookService webhookService;

    public MessageWebhook(WebhookService webhookService) {
        this.webhookService = webhookService;
    }

    @PostMapping("receive")
    public ResponseEntity receiveMessage(@RequestBody ExternalMessageDto message) throws InvalidModelException {
          webhookService.receiveMessage(message);
        return ResponseEntity.ok().body(null);

    }



}
