package data.controller;

import data.dto.ApiResponse;
import data.dto.CategoryDto;
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
    public ResponseEntity<ApiResponse<?>> createCategory(String name, MultipartFile file) {
        categoryService.createCategory(name, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created());
    }

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<CategoryDto>>> findAllCategory() {
        return ResponseEntity.ok()
                .body(ApiResponse.ok(categoryService.findAllCategory()));
    }
}
