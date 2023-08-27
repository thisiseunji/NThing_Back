package data.controller;

import data.dto.FileDto;
import data.service.FileService;
import data.service.PurchaseService;
import data.dto.PurchaseDto;
import data.util.MultiFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final FileService fileService;
    private final MultiFileUtils multiFileUtils;

    @Autowired
    public PurchaseController(PurchaseService purchaseService, FileService fileService, MultiFileUtils multiFileUtils) {
        this.purchaseService = purchaseService;
        this.fileService = fileService;
        this.multiFileUtils = multiFileUtils;
    }

    @PostMapping("/purchase")
    public void createPurchase(PurchaseDto.Request purchaseRequest) {
        int id = purchaseService.createPurchase(purchaseRequest);
        List<FileDto.Request> files = multiFileUtils.uploadFiles(purchaseRequest.getFiles());
        fileService.saveFiles(id, files);
    }

    @GetMapping("/purchases")
    public List<PurchaseDto.Summary> findAllPurchase(String search_keyword, String sort) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("search_keyword", search_keyword);
        map.put("sort", sort);
        return purchaseService.findAllPurchase(map);
    }

    @GetMapping("/purchase/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        PurchaseDto.Detail detail = purchaseService.findPurchaseById(id);

        if (detail == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Purchase not found");

        return ResponseEntity.ok(detail);
    }

    @PatchMapping("/purchase")
    public void updatePurchase(PurchaseDto.Request purchaseRequest) {
        // 1. 게시글 수정
        purchaseService.updatePurchase(purchaseRequest);
        // 2. 삭제할 파일 정보 조회 (from database)
        List<FileDto.Response> deleteFiles = fileService.findAllFileByIds(fileService.findAllIdsByPurchaseId(purchaseRequest.getId()));
        // 3. 파일 삭제 (from disk)
        multiFileUtils.deleteFiles(deleteFiles);
        // 4. 파일 삭제 (from database)
        fileService.deleteAllFileByIds(fileService.findAllIdsByPurchaseId(purchaseRequest.getId()));
        // 5. 파일 업로드 (to disk)
        List<FileDto.Request> uploadFiles = multiFileUtils.uploadFiles(purchaseRequest.getFiles());
        // 6. 파일 정보 저장 (to database)
        fileService.saveFiles(purchaseRequest.getId(), uploadFiles);
    }

    @DeleteMapping("/purchase/{id}")
    public void deletePurchase(@PathVariable int id) {
        // 1. 게시글 삭제
        purchaseService.deletePurchase(id);
        // 2. 삭제할 파일 정보 조회 (from database)
        List<FileDto.Response> deleteFiles = fileService.findAllFileByIds(fileService.findAllIdsByPurchaseId(id));
        // 3. 파일 삭제 (from disk)
        multiFileUtils.deleteFiles(deleteFiles);
        // 4. 파일 삭제 (from database)
        fileService.deleteAllFileByIds(fileService.findAllIdsByPurchaseId(id));
    }

    // 파일 리스트 조회
    @GetMapping("/purchase/{purchaseId}/files")
    public List<FileDto.Response> findAllFileByPurchaseId(@PathVariable int purchaseId) {
        return fileService.findAllFileByPurchaseId(purchaseId);
    }
}
