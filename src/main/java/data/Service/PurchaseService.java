package data.Service;

import data.dto.PurchaseDto;
import data.mapper.PurchaseMapper;
import data.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class PurchaseService {

    private final PurchaseMapper purchaseMapper;

    @Autowired
    public PurchaseService(PurchaseMapper purchaseMapper) {
        this.purchaseMapper = purchaseMapper;
    }

    public void createPurchase(PurchaseDto.Request purchase) {
        purchaseMapper.createPurchase(purchase);
    }

    public List<PurchaseDto.Summary> findAllPurchase(HashMap<String, Object> map) {
        return purchaseMapper.findAllPurchase(map);
    }

    public PurchaseDto.Detail findPurchaseById(int id) {
        return purchaseMapper.findPurchaseById(id);
    }

    public void updatePurchase(PurchaseDto.Request purchase) {
        purchaseMapper.updatePurchase(purchase);
    }

    public void deletePurchase(int id) {
        FileUploadUtil.deleteFile(findPurchaseById(id).getImage());
        purchaseMapper.deletePurchase(id);
    }
}
