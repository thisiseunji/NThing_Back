package data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class ChatMessageDto {

    public enum WebSocketMessageType {
        ENTER,
        OUIT,
        WRITE,
        LIST // 의 경우 해당채팅방에 대한 모든 채팅 보여줄 것? 접속 하면서 바로
    }

    public enum ChatMessageType {
        NORMAL,
        IMAGE,
        KICK_OUT,
        COMPLETE
    }

    @JsonProperty("web_socket_message_type")
    private WebSocketMessageType webSocketMessageType;
    @JsonProperty("chat_message_type")
    private ChatMessageType chatMessageType;
    @JsonProperty("id")
    private int id;
    @JsonProperty("sender")
    private String sender;
    @JsonProperty("message")
    private String message;
    @JsonProperty("sent_at")
    private Timestamp sentAt;
    @JsonProperty("chat_room_id")
    private int chatRoomId;

    // 임시 테스트용
    @JsonProperty("sender_id")
    private int senderId;
}
