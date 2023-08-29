package data.service;

import data.dto.FileDto;
import data.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;

    @Autowired
    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public void saveFiles(int purchase_id, List<FileDto.Request> files) {
        if(CollectionUtils.isEmpty(files)) {
            return;
        }
        for(FileDto.Request file : files) {
            file.setPurchase_id(purchase_id);
        }
        fileMapper.saveAll(files);
    }

    /**
     * 파일 리스트 조회
     * @param purchaseId - 게시글 번호 (FK)
     * @return 파일 리스트
     */
    public List<FileDto.Response> findAllFileByPurchaseId(int purchaseId) {
        return fileMapper.findAllByPurchaseId(purchaseId);
    }

    /**
     * ids 리스트 조회
     * @param purchaseId - 게시글 번호 (FK)
     * @return ids 리스트
     */
    public List<Integer> findAllIdsByPurchaseId(int purchaseId) {
        return fileMapper.findAllIdsByPurchaseId(purchaseId);
    }

    /**
     * 파일 리스트 조회
     * @param ids - PK 리스트
     * @return 파일 리스트
     */
    public List<FileDto.Response> findAllFileByIds(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return fileMapper.findAllByIds(ids);
    }

    /**
     * 파일 삭제 (from Database)
     * @param ids - PK 리스트
     */
    @Transactional
    public void deleteAllFileByIds(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        fileMapper.deleteAllByIds(ids);
    }
}
