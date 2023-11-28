package data.service;

import data.dto.ChatRoomDto;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ChatRoomManger {
    Map<Integer, ChatRoomDto> chatRooms = new HashMap<>();

    // test용
    public ChatRoomManger() {
        chatRooms.put(1, new ChatRoomDto(1, 1));
    }

    public void addChatRoom(ChatRoomDto chatRoom) {
        chatRooms.put(chatRoom.getId(), chatRoom);
    }

    public ChatRoomDto getChatRoom(int roomId) {
        return chatRooms.get(roomId);
    }

    public Set<Integer> getAllRoomIds() {
        return chatRooms.keySet();
    }

    public List<ChatRoomDto> getAllRooms() {return new ArrayList<>(chatRooms.values());}

    public void removeChatRoom(int roomId){
        chatRooms.remove(roomId);
    }

}
