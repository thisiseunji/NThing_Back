package data.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;


public class LikeDto {

    @Setter
    @Getter
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class Like {
        private int id;
        private int userId;
        private int purchaseId;
    }

    @Setter
    @Getter
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class LikeResponse {
        private boolean value;

        public LikeResponse(boolean value) {
            this.value = value;
        }
    }
}
