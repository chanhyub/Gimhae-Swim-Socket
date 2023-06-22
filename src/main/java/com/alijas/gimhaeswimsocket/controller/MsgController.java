package com.alijas.gimhaeswimsocket.controller;

import com.alijas.gimhaeswimsocket.dto.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MsgController {

    @MessageMapping("/message/{roomId}")
    @SendTo("/sub/start/{roomId}")
    public Message message(@DestinationVariable String roomId, Message message) {
        return message;
    }

    @MessageMapping("/connect/{roomId}")
    @SendTo("/sub/connect/{roomId}")
    public String connect(@DestinationVariable String roomId, String connect) {
        return connect;
    }
}
