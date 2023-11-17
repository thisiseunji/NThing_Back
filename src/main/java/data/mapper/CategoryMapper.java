package data.mapper;

import data.dto.CategoryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    void createCategory(CategoryDto categoryDto);
    List<CategoryDto> findAllCategory();
    boolean existsByName(String name);
}
