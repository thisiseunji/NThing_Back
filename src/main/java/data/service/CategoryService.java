package data.service;

import data.dto.CategoryDto;
import data.dto.FileDto;
import data.exception.DuplicateCategoryNameException;
import data.mapper.CategoryMapper;
import data.util.MultiFileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    @Value("${local-path-test}")
    private String urlPath;

    private final CategoryMapper categoryMapper;
    private final MultiFileUtils multiFileUtils;

    public void createCategory(String name, MultipartFile file) {
        try {
            if (categoryMapper.existsByName(name)) {
                throw new DuplicateCategoryNameException("Category with name "+name+" already exists.");
            }

            FileDto.Request fileDto = multiFileUtils.uploadFile(file, "category");
            CategoryDto categoryDto = CategoryDto.builder()
                    .name(name)
                    .image(fileDto.getSave_name())
                    .build();

            categoryMapper.createCategory(categoryDto);
        } catch (DuplicateCategoryNameException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error while creating category", e);
        }

    }

    public List<CategoryDto> findAllCategory() {
        return categoryMapper.findAllCategory()
                .stream()
                .map(this::generateImageUrl)
                .collect(Collectors.toList());
    }

    private CategoryDto generateImageUrl(CategoryDto categoryDto) {
        if (Objects.nonNull(categoryDto.getImage()) && !categoryDto.getImage().isEmpty()) {
            categoryDto.setImage(urlPath + categoryDto.getImage());
        }
        return categoryDto;
    }
}
