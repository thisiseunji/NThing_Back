package data.controller;

import data.Service.PurchaseService;
import data.dto.PurchaseDto;
import data.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@RestController
public class PurchaseController {

    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("/purchase")
    public void createPurchase(
            @RequestParam(value = "image", required = false) MultipartFile imageFile,
            @ModelAttribute PurchaseDto.Request purchaseRequest
    ) {
        purchaseService.createPurchase(purchaseRequest);
    }

    @GetMapping("/purchases")
    public List<PurchaseDto.Summary> findAllPurchase(String search_keyword, String sort) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("search_keyword", search_keyword);
        map.put("sort", sort);
        return purchaseService.findAllPurchase(map);
    }

    @GetMapping("/purchase/{id}")
    public PurchaseDto.Detail findById(@PathVariable int id) {
        return purchaseService.findPurchaseById(id);
    }

    @PatchMapping("/purchase")
    public void updatePurchase(
            @RequestParam(value = "image", required = false) MultipartFile imageFile,
            @ModelAttribute PurchaseDto.Request purchaseRequest
    ){
        PurchaseDto.Detail purchase = purchaseService.findPurchaseById(purchaseRequest.getId());

        if(StringUtils.hasText(purchase.getImage())) {
            FileUploadUtil.deleteFile(purchase.getImage());
        }

        purchaseService.updatePurchase(purchaseRequest);
    }

    @DeleteMapping("/purchase/{id}")
    public void deletePurchase(@PathVariable int id) {
        purchaseService.deletePurchase(id);
    }
}
