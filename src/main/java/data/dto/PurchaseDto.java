package data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import data.util.FileUploadUtil;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;

public class PurchaseDto {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Info {
        private int id;
        private String title;
        private String description;
        private Float latitude;
        private Float longitude;
        private Timestamp date;
        private int denominator;
        private int numerator;
        private boolean status;
        private int price;
        private String place;
        private String image;
        private Timestamp updatedAt;
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
        private Float latitude;
        private Float longitude;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Timestamp date;
        private int denominator;
        private int numerator;
        private boolean status;
        private int price;
        private String place;
        private String image;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Timestamp updatedAt;
        private int manager_id;
        private int category_id;

        public void setImage(MultipartFile imageFile) throws IOException {
            if(!imageFile.isEmpty()) {
                this.image = FileUploadUtil.uploadFile(imageFile);
            }
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class Summary {
        private int id;
        private String title;
        private Float latitude;
        private Float longitude;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Timestamp date;
        private int denominator;
        private int numerator;
        private int price;
        private String place;
        private boolean status;
        private String image;
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
        private Float latitude;
        private Float longitude;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Timestamp date;
        private int denominator;
        private int numerator;
        private boolean status;
        private int price;
        private String place;
        private String image;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Timestamp updatedAt;
        private int managerId;
        private int categoryId;
        private String categoryName;
        private boolean isLiked;
    }
}
