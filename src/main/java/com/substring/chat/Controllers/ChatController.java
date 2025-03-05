package com.substring.chat.Controllers;

import com.substring.chat.config.AppConstants;
import com.substring.chat.entity.Message;
import com.substring.chat.entity.Room;
import com.substring.chat.load.MessageRequest;
import com.substring.chat.repositories.RoomRepositories;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Controller
@CrossOrigin(AppConstants.FRONTED_URL)
public class ChatController {

    private RoomRepositories roomRepo;

    public ChatController(RoomRepositories roomRepo) {
        this.roomRepo = roomRepo;
    }

    // for sending and receiving messages
    @MessageMapping("/sendMessage/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public Message sendMessages(@DestinationVariable String roomId,
            @RequestBody MessageRequest request){

        Room room = roomRepo.findByRoomId(request.getRoomId());

        Message message = new Message();
        message.setContent(request.getContent());
        message.setSender(request.getSender());
        message.setTimeStamp(LocalDateTime.now());

        if(room != null){
            room.getMessages().add(message);
            roomRepo.save(room);
        }else{
            throw new RuntimeException("Room not found!!");
        }
        return message;
    }

}
