package data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

        @NotBlank(message="제목은 필수 입니다.")
        private String title;

        @NotBlank(message="설명은 필수 입니다.")
        private String description;

        @NotNull(message="위도는 필수 입니다.")
        private Double latitude;

        @NotNull(message="경도는 필수 입니다.")
        private Double longitude;

        @NotBlank(message="거래 시간은 필수 입니다.")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        private Timestamp date;

        @NotNull(message="총 할당은 필수 입니다.")
        @Min(value = 0, message = "Denominator must be greater than or equal to 0")
        private int denominator;

        @NotNull(message="할당은 필수 입니다.")
        private int numerator;

        private boolean status;

        @NotNull(message="가격은 필수 입니다.")
        private int price;

        @NotBlank(message="거래 장소는 필수 입니다.")
        private String place;

        private List<MultipartFile> files = new ArrayList<>();

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Timestamp updatedAt;

        private int manager_id;

        @NotNull(message="카테고리는 필수 입니다.")
        private int category_id;

        // 삭제할 첨부파일 id List
        private List<Integer> removeFileIds = new ArrayList<>();
    }

    @Getter
    @Builder
    @Setter
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
        private String image;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
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
        private String manager;
        private int categoryId;
        private String categoryName;
        private boolean isLiked;

        private List<String> images;
    }
}
