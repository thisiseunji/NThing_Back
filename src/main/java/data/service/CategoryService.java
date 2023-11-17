package data.service;

import data.dto.CategoryDto;
import data.dto.FileDto;
import data.exception.DuplicateCategoryNameException;
import data.mapper.CategoryMapper;
import data.util.MultiFileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final MultiFileUtils multiFileUtils;
    private final HttpServletRequest request;

    public void createCategory(String name, MultipartFile file) {
        try {
            if (categoryMapper.existsByName(name)) {
                throw new DuplicateCategoryNameException("Category with name "+name+" already exists.");
            }
            String saveRoute = "category";
            FileDto.Request fileDto = multiFileUtils.uploadFile(file, saveRoute);
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
            categoryDto.setImage(getDomain() + categoryDto.getImage());
        }
        return categoryDto;
    }

    private String getDomain() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String scheme = request.getScheme();
            String serverName = request.getServerName();
            int serverPort = request.getServerPort();
            return scheme + "://" + serverName + ":" + serverPort + "/" + "file/";
        }
        return "";
    }
}
