package data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

@Data
@Alias("PurchaseDto")
public class PurchaseDto {
    private int id;
    private String title;
    private String description;
    private Float latitude;
    private Float longitude;
    private Timestamp purchaseDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private int totalNumber;
    private int managerNumber;
    private boolean status;
    private int price;
    private String address;
    private String detailedAddress;
    private int managerId;
}
