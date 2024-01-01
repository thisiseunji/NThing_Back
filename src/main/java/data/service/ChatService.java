package data.service;

import data.dto.ChatMessageDto;
import data.dto.ChatRoomDto;
import data.mapper.ChatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    @Autowired
    ChatRoomManger chatRoomManager;

    @Autowired
    ChatMapper chatMapper;

    public List<ChatRoomDto> findAllRoom() {
        return chatRoomManager.getAllRooms();
    }

    public ChatRoomDto findRoomById(int id) {
        return chatRoomManager.getChatRoom(id);
    }

    // purchase가 생기면, 채팅 방 생성되도록 구현
    public void createChatRoom(ChatRoomDto chatRoomDto) {
        chatMapper.createChatRoom(chatRoomDto);
        // 싱글톤으로 관리
        chatRoomManager.addChatRoom(chatRoomDto);
    }

    public ChatRoomDto _findChatRoomByPurchaseId(int purchaseId) {
        return chatMapper._findChatRoomByPurchaseId(purchaseId);
    }

    public void createChatMessage(ChatMessageDto chatMessage) {
        chatMapper.createChatMessage(chatMessage);
    }

    // 유저 id넣기
    public void createOrUpdateChatRoomUser(ChatRoomDto chatRoomDto) {chatMapper.createOrUpdateChatRoomUser(chatRoomDto);} //룸과 유저의 id가 있어야함

    public void deleteChatRoomUser(ChatRoomDto chatRoomDto) { chatMapper.deleteChatRoomUser(chatRoomDto);}

}