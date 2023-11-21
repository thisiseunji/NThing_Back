package data.controller;

import data.constants.ErrorCode;
import data.dto.FileDto;
import data.exception.ValidationException;
import data.service.FileService;
import data.service.PurchaseService;
import data.dto.PurchaseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final FileService fileService;

    @PostMapping("/purchase")
    public ResponseEntity<?> createPurchase(
            @RequestHeader("Authorization") String token,
            @Valid PurchaseDto.Request purchaseRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult, ErrorCode.VALIDATION_ERROR);
        }
        purchaseService.createPurchase(purchaseRequest, token);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/purchases")
    public ResponseEntity<List<PurchaseDto.Summary>> findAllPurchase(
            String search_keyword,
            String sort,
            double latitude,
            double longitude,
            int radius,
            @RequestParam(defaultValue = "true") boolean status
    ) {
        Map<String, Object> map = new HashMap<>();
        map.put("search_keyword", search_keyword);
        map.put("sort", sort);
        map.put("latitude", latitude);
        map.put("longitude", longitude);
        map.put("radius", radius);
        map.put("status", status);
        return ResponseEntity.status(HttpStatus.OK).body(purchaseService.findAllPurchase(map));
    }

    @GetMapping("/purchase/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        PurchaseDto.Detail detail = purchaseService.findPurchaseById(id);
        return ResponseEntity.ok(detail);
    }

    @PatchMapping("/purchase/{id}")
    public void updatePurchase(
            @RequestHeader("Authorization") String token,
            @PathVariable int id,
            PurchaseDto.Request purchaseRequest
    ) {
        purchaseService.updatePurchase(purchaseRequest, token, id);
    }

    @DeleteMapping("/purchase/{id}")
    public ResponseEntity<?> deletePurchase(@PathVariable int id, @RequestHeader("Authorization") String token) {
        purchaseService.deletePurchase(id, token);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/purchase/{purchaseId}/files")
    public List<FileDto.Response> findAllFileByPurchaseId(@PathVariable int purchaseId) {
        return fileService.findAllFileByPurchaseId(purchaseId);
    }
}
