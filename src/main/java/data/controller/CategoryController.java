package data.controller;

import data.dto.ApiResult;
import data.dto.CategoryDto;
import data.dto.ErrorResponse;
import data.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "category", description = "category API")
@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "카테고리 생성", description = "카테고리를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "카테고리 생성 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResult.class))),
            @ApiResponse(responseCode = "400", description = "카테고리 생성 실패", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping(value = "/category", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResult<?>> createCategory(
            @Parameter(name = "name", description = "카테고리 이름", required = true)
            @RequestParam(value = "name", required = true) String name,
            @Parameter(name = "file", description = "카테고리 이미지", required = true)
            @RequestParam(value = "file", required = true) MultipartFile file
    ) {
        categoryService.createCategory(name, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResult.created());
    }

    @Operation(summary = "카테고리 목록 조회", description = "카테고리 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리 목록 조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResult.class))),
    })
    @GetMapping("/categories")
    public ResponseEntity<ApiResult<List<CategoryDto>>> findAllCategory() {
        return ResponseEntity.ok()
                .body(ApiResult.ok(categoryService.findAllCategory()));
    }
}
