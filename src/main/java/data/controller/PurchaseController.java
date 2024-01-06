package data.controller;

import data.constants.ErrorCode;
import data.dto.ApiResponse;
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
    public ResponseEntity<ApiResponse<PurchaseDto.Detail>> createPurchase(
            @RequestHeader("Authorization") String token,
            @Valid PurchaseDto.Request purchaseRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult, ErrorCode.VALIDATION_ERROR);
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.created(purchaseService.createPurchase(purchaseRequest, token)));
    }

    @GetMapping("/purchases")
    public ResponseEntity<ApiResponse<List<PurchaseDto.Summary>>> findAllPurchase(
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

        return ResponseEntity.ok(ApiResponse.ok(purchaseService.findAllPurchase(map)));
    }

    @GetMapping("/purchase/{id}")
    public ResponseEntity<ApiResponse<PurchaseDto.Detail>> findById(@PathVariable int id) {
        return ResponseEntity.ok(ApiResponse.ok(purchaseService.findPurchaseById(id)));
    }

    @PatchMapping("/purchase/{id}")
    public ResponseEntity<ApiResponse<PurchaseDto.Detail>> updatePurchase(
            @RequestHeader("Authorization") String token,
            @PathVariable int id,
            PurchaseDto.Request purchaseRequest
    ) {
        return ResponseEntity.ok(ApiResponse.ok(purchaseService.updatePurchase(purchaseRequest, token, id)));
    }

    @DeleteMapping("/purchase/{id}")
    public ResponseEntity<ApiResponse<?>> deletePurchase(@PathVariable int id, @RequestHeader("Authorization") String token) {
        purchaseService.deletePurchase(id, token);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.noContent());
    }

    @GetMapping("/purchase/{purchaseId}/files")
    public ResponseEntity<ApiResponse<List<FileDto.Response>>> findAllFileByPurchaseId(@PathVariable int purchaseId) {
        return ResponseEntity.ok(ApiResponse.ok(fileService.findAllFileByPurchaseId(purchaseId)));
    }
}
