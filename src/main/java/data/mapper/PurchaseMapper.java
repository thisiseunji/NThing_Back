package data.mapper;

import data.dto.PurchaseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface PurchaseMapper {
    int createPurchase(PurchaseDto.Request purchase);
    List<PurchaseDto.Summary> findAllPurchase(HashMap<String, Object> map);
    PurchaseDto.Detail findPurchaseById(int id);
    void updatePurchase(PurchaseDto.Request purchase);
    void deletePurchase(int id);
}
