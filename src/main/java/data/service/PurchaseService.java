package data.service;

import data.constants.ErrorCode;
import data.dto.FileDto;
import data.dto.PurchaseDto;
import data.exception.PurchaseNotFoundException;
import data.mapper.FileMapper;
import data.mapper.PurchaseMapper;
import data.util.JwtProvider;
import data.util.MultiFileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseMapper purchaseMapper;
    private final FileService fileService;
    private final FileMapper fileMapper;
    private final MultiFileUtils multiFileUtils;
    private final JwtProvider jwtProvider;
    private final HttpServletRequest request;

    public void createPurchase(PurchaseDto.Request purchaseRequest, String token) {
        int userId = jwtProvider.parseJwt(token);
        purchaseRequest.setManager_id(userId);
        purchaseMapper.createPurchase(purchaseRequest);
        int purchaseId = purchaseRequest.getId();
        List<FileDto.Request> files = multiFileUtils.uploadFiles(purchaseRequest.getFiles(), "purchase");
        fileService.saveFiles(purchaseId, files);
    }

    public List<PurchaseDto.Summary> findAllPurchase(Map<String, Object> map) {
            // 첫 번째 쿼리: 위치 설정
            purchaseMapper.setLocation(Map.of("latitude", map.get("latitude"), "longitude", map.get("longitude")));
            // 두 번째 쿼리: 데이터 조회
            List<PurchaseDto.Summary> result = purchaseMapper.findAllPurchase(map);
            List<PurchaseDto.Summary> generatePurchaseDtoList = new ArrayList<>();
            for (PurchaseDto.Summary purchaseDto : result) {
                purchaseDto.setImage(multiFileUtils.getDomain() + purchaseDto.getImage());
                generatePurchaseDtoList.add(purchaseDto);
            }
            return generatePurchaseDtoList;
    }

    public PurchaseDto.Detail findPurchaseById(int id) {
        PurchaseDto.Detail detail = purchaseMapper.findPurchaseById(id);

        if (detail == null)
            throw new PurchaseNotFoundException("Purchase not found for ID: " + id, ErrorCode.PURCHASE_NOT_FOUND);

        List<FileDto.Response> fileList = fileMapper.findAllByPurchaseId(id);
        List<String> imageList = new ArrayList<>();

        if (!fileList.isEmpty()) {
            List<FileDto.Response> generatedFiles = multiFileUtils.generateFilePath(fileList);
            for (FileDto.Response file : generatedFiles) {
                imageList.add(file.getSaveName());
            }
        }
        detail.setImages(imageList);
        return detail;
    }

    public void updatePurchase(PurchaseDto.Request purchaseRequest, String token, int id) {
        int userId = jwtProvider.parseJwt(token);
        Map<String, Object> map = Map.of("id", id, "user_id", userId);
        if (purchaseMapper.findPurchaseByIdAndUserId(map)) {
            purchaseMapper.updatePurchase(purchaseRequest);
            List<FileDto.Request> uploadFiles = multiFileUtils.uploadFiles(purchaseRequest.getFiles(), "purchase");
            fileService.saveFiles(purchaseRequest.getId(), uploadFiles);
        } else {
            throw new PurchaseNotFoundException("Purchase not found for id: " + id, ErrorCode.PURCHASE_NOT_FOUND);
        }


    }

    public void deletePurchase(int id, String token) {
        int userId = jwtProvider.parseJwt(token);
        Map<String, Object> map = Map.of("id", id, "user_id", userId);
        if (purchaseMapper.findPurchaseByIdAndUserId(map)) {
            purchaseMapper.deletePurchase(id);
            List<FileDto.Response> deleteFiles = fileService.findAllFileByIds(fileService.findAllIdsByPurchaseId(id));
            multiFileUtils.deleteFiles(deleteFiles);
            fileService.deleteAllFileByIds(fileService.findAllIdsByPurchaseId(id));
        } else {
            throw new PurchaseNotFoundException("Purchase not found for id: " + id, ErrorCode.PURCHASE_NOT_FOUND);
        }
    }
}
