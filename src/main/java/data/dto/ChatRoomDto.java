package data.dto;

import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class ChatRoomDto {
    private int id;
    private int purchaseId;
    private Timestamp createdAt;
    private Boolean isDeleted;
    private Boolean isCompleted;

    private Set<WebSocketSession> sessions = new HashSet<>();

    // 임시? 중간테이블 입력받기 위함
    private int userId;

    @Builder
    public ChatRoomDto(int id, int purchaseId) {
        this.id= id;
        this.purchaseId = purchaseId;
    }

    @Builder
    public ChatRoomDto(int id, int purchaseId, Timestamp createdAt, Boolean isDeleted, Boolean isCompleted) {
        this.id = id;
        this.purchaseId = purchaseId;
        this.createdAt = createdAt;
        this.isDeleted = isDeleted;
        this.isCompleted = isCompleted;
    }
}
