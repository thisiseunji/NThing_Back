package data.mapper;

import data.dto.CollegeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface CollegeMapper {
    CollegeDto selectCollegeById(Integer id);
    List<CollegeDto> selectCollegeList(String search_keyword);
    public void insertCollege(HashMap<String, String> map);
}
