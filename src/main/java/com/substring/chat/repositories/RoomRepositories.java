package com.substring.chat.repositories;

import com.substring.chat.entity.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepositories extends MongoRepository<Room,String> {

    //get room using roomid

    Room findByRoomId(String roomId);
}
