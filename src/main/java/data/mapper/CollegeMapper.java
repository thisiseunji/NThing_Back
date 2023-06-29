package data.mapper;

import data.dto.CollegeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CollegeMapper {
    List<CollegeDto> selectCollegeList();
}
