package data.service;

import data.dto.PurchaseDto;
import data.mapper.PurchaseMapper;
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

    public int createPurchase(PurchaseDto.Request purchaseRequest) {
        purchaseMapper.createPurchase(purchaseRequest);
        return purchaseRequest.getId();
    }

    public List<PurchaseDto.Summary> findAllPurchase(HashMap<String, Object> map) {
        return purchaseMapper.findAllPurchase(map);
    }

    public PurchaseDto.Detail findPurchaseById(int id) {
        return purchaseMapper.findPurchaseById(id);
    }

    public void updatePurchase(PurchaseDto.Request purchaseRequest) {
        purchaseMapper.updatePurchase(purchaseRequest);
    }

    public void deletePurchase(int id) {
        purchaseMapper.deletePurchase(id);
    }
}
