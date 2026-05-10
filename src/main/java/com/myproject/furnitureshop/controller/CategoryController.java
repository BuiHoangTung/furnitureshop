package com.myproject.furnitureshop.controller;

import com.myproject.furnitureshop.dto.request.*;
import com.myproject.furnitureshop.dto.response.*;
import com.myproject.furnitureshop.ratelimit.RateLimit;
import com.myproject.furnitureshop.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RateLimit(requests = 50)
    @PostMapping
    public ResponseEntity<SuccessResponse<CategoryCreationResponse>> createCategory(@Valid @RequestBody CategoryCreationRequest request) {
        CategoryCreationResponse creationResponse = this.categoryService.createCategory(request);

        SuccessResponse<CategoryCreationResponse> response = SuccessResponse.of("Create category successfully.", creationResponse);

        return ResponseEntity.ok().body(response);
    }

    @RateLimit(requests = 50)
    @PostMapping("/file-uploading")
    public ResponseEntity<SuccessResponse<CategoryFileUploadResponse>> uploadCategoryImage(@Valid @ModelAttribute CategoryFileUploadRequest request) {
        CategoryFileUploadResponse fileUploadResponse = this.categoryService.uploadCategoryFile(request);

        SuccessResponse<CategoryFileUploadResponse> response = SuccessResponse.of("Upload successfully.", fileUploadResponse);

        return ResponseEntity.ok().body(response);
    }

    @RateLimit(requests = 50)
    @PatchMapping("/banner")
    public ResponseEntity<SuccessResponse<CategoryFileUploadResponse>> updateBanner(@Valid @ModelAttribute CategoryFileUpdateRequest request) {
        CategoryFileUploadResponse fileUploadResponse = this.categoryService.updateFile(request, true);

        SuccessResponse<CategoryFileUploadResponse> response = SuccessResponse.of("Update banner successfully.", fileUploadResponse);

        return ResponseEntity.ok(response);
    }

    @RateLimit(requests = 50)
    @PatchMapping("/thumbnail")
    public ResponseEntity<SuccessResponse<CategoryFileUploadResponse>> updateThumbnail(@Valid @ModelAttribute CategoryFileUpdateRequest request) {
        CategoryFileUploadResponse fileUploadResponse = this.categoryService.updateFile(request, false);

        SuccessResponse<CategoryFileUploadResponse> response = SuccessResponse.of("Update thumbnail successfully.", fileUploadResponse);

        return ResponseEntity.ok(response);
    }

    @RateLimit(requests = 50)
    @PatchMapping("/{id}")
    public ResponseEntity<SuccessResponse<CategoryInfoUpdateResponse>> updateCategoryInfo(
            @Valid @RequestBody CategoryInfoUpdateRequest request,
            @PathVariable long id
    ) {
        CategoryInfoUpdateResponse infoUpdateResponse = this.categoryService.updateCategoryInfo(request, id);

        SuccessResponse<CategoryInfoUpdateResponse> response = SuccessResponse.of("Update category information successfully.", infoUpdateResponse);

        return ResponseEntity.ok().body(response);
    }

    @RateLimit(requests = 25)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> softDeleteCategory(@PathVariable long id) {
        this.categoryService.softDeleteCategory(id);

        return ResponseEntity.ok().body(SuccessResponse.of("Soft delete category successfully.", null));
    }

    @RateLimit
    @GetMapping("/{name}")
    public ResponseEntity<SuccessResponse<CategoryNode>> getCategories(@PathVariable String name) {
        SuccessResponse<CategoryNode> response = SuccessResponse.of("successful.", this.categoryService.getCategoryHierarchy(name));

        return ResponseEntity.ok().body(response);
    }

    @RateLimit
    @GetMapping("active/{name}")
    public ResponseEntity<SuccessResponse<CategoryNode>> getOnlyActiveCategories(@PathVariable String name) {
        SuccessResponse<CategoryNode> response = SuccessResponse.of("Successful.", this.categoryService.getOnlyActiveCategoryHierarchy(name));

        return ResponseEntity.ok().body(response);
    }

    @RateLimit
    @GetMapping("/root")
    public ResponseEntity<SuccessResponse<List<CategoryNode>>> getOnlyActiveRootCategories() {
        SuccessResponse<List<CategoryNode>> response =
                SuccessResponse.of("Successful.", this.categoryService.getOnlyActiveRootCategories());

        return ResponseEntity.ok().body(response);
    }

    @RateLimit
    @GetMapping
    public ResponseEntity<SuccessResponse<List<CategoryNode>>> getCategoryByLevel(@RequestParam String level) {
        SuccessResponse<List<CategoryNode>> response =
                SuccessResponse.of("Successful.", this.categoryService.getCategoriesByLevel(level));

        return ResponseEntity.ok().body(response);
    }
}
