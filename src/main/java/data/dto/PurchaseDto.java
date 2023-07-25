package data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PurchaseDto {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Purchase {
        private int id;
        private String title;
        private String description;
        private Double latitude;
        private Double longitude;
        private Timestamp date;
        private int denominator;
        private int numerator;
        private boolean status;
        private int price;
        private String place;
        private Timestamp updatedAt;
        private boolean deleteYn;
        private Timestamp deletedAt;
        private int managerId;
        private int categoryId;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private int id;
        private String title;
        private String description;
        private Double latitude;
        private Double longitude;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Timestamp date;
        private int denominator;
        private int numerator;
        private boolean status;
        private int price;
        private String place;
        private List<MultipartFile> files = new ArrayList<>();
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Timestamp updatedAt;
        private int manager_id;
        private int category_id;

        // 삭제할 첨부파일 id List
        private List<Integer> removeFileIds = new ArrayList<>();
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class Summary {
        private int id;
        private String title;
        private Double latitude;
        private Double longitude;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Timestamp date;
        private int denominator;
        private int numerator;
        private int price;
        private String place;
        private boolean status;
        private boolean isLiked;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class Detail {
        private int id;
        private String title;
        private String description;
        private Double latitude;
        private Double longitude;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Timestamp date;
        private int denominator;
        private int numerator;
        private boolean status;
        private int price;
        private String place;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Timestamp updatedAt;
        private int managerId;
        private int categoryId;
        private String categoryName;
        private boolean isLiked;
    }
}
