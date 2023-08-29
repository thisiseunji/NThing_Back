package data.service;

import data.dto.FileDto;
import data.dto.PurchaseDto;
import data.mapper.FileMapper;
import data.mapper.PurchaseMapper;
import data.util.MultiFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class PurchaseService {

    private final PurchaseMapper purchaseMapper;
    private final FileMapper fileMapper;
    private final MultiFileUtils multiFileUtils;

    @Autowired
    public PurchaseService(PurchaseMapper purchaseMapper, FileMapper fileMapper, MultiFileUtils multiFileUtils) {
        this.purchaseMapper = purchaseMapper;
        this.fileMapper = fileMapper;
        this.multiFileUtils = multiFileUtils;
    }

    public int createPurchase(PurchaseDto.Request purchaseRequest) {
        purchaseMapper.createPurchase(purchaseRequest);
        return purchaseRequest.getId();
    }

    public List<PurchaseDto.Summary> findAllPurchase(HashMap<String, Object> map) {
        return purchaseMapper.findAllPurchase(map);
    }

    public PurchaseDto.Detail findPurchaseById(int id) {
        PurchaseDto.Detail detail = purchaseMapper.findPurchaseById(id);

        if (detail == null)
            return  null;

        List<FileDto.Response> images = fileMapper.findAllByPurchaseId(id);

        if (!images.isEmpty()) {
            List<String> generatedImages = multiFileUtils.generateFilePath(images);
            detail.setImages(generatedImages);
        } else
            detail.setImages(new ArrayList<>());

        return detail;
    }

    public void updatePurchase(PurchaseDto.Request purchaseRequest) {
        purchaseMapper.updatePurchase(purchaseRequest);
    }

    public void deletePurchase(int id) {
        purchaseMapper.deletePurchase(id);
    }
}
