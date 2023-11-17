package data.controller;

import data.dto.CategoryDto;
import data.exception.DuplicateCategoryNameException;
import data.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/category")
    public ResponseEntity<?> createCategory(String name, MultipartFile file) {
        try {
            categoryService.createCategory(name, file);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (DuplicateCategoryNameException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category name already exists.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }

    }

    @GetMapping("/categories")
    public List<CategoryDto> findAllCategory() {
        return categoryService.findAllCategory();
    }
}
