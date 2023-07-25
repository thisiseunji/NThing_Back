package data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

public class FileDto {

    @Getter
    public static class Request {
        private int id;
        private String original_name;
        private String save_name;
        private int size;
        private int purchase_id;

        @Builder
        public Request(String original_name, String save_name, int size) {
            this.original_name = original_name;
            this.save_name = save_name;
            this.size = size;
        }

        public void setPurchase_id(int purchase_id) {
            this.purchase_id = purchase_id;
        }
    }

    @Getter
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class Response {
        private int id;
        private String originalName;
        private String saveName;
        private int size;
        private boolean deleteYn;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Timestamp createdDate;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Timestamp deletedDate;
        private int purchaseId;
    }

}
