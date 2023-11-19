package data.mapper;

import data.dto.FileDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {
    void saveAll(List<FileDto.Request> files);
    List<FileDto.Response> findAllByPurchaseId(int purchaseId);
    List<FileDto.Response> findAllByIds(List<Integer> ids);
    List<Integer> findAllIdsByPurchaseId(int purchaseId);
    void deleteAllByIds(List<Integer> ids);
}
