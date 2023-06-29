package data.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("PurchaseUserDto")
public class PurchaseUserDto {
    private int id;
    private int userId;
    private int purchaseId;
}
