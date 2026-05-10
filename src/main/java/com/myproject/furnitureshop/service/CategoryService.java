package com.myproject.furnitureshop.service;

import com.myproject.furnitureshop.dto.request.*;
import com.myproject.furnitureshop.dto.response.CategoryCreationResponse;
import com.myproject.furnitureshop.dto.response.CategoryFileUploadResponse;
import com.myproject.furnitureshop.dto.response.CategoryInfoUpdateResponse;
import com.myproject.furnitureshop.dto.response.CategoryNode;
import com.myproject.furnitureshop.enums.CategoryLevel;

import java.util.List;

public interface CategoryService {
    CategoryCreationResponse createCategory(CategoryCreationRequest request);
    CategoryFileUploadResponse uploadCategoryFile(CategoryFileUploadRequest request);
    CategoryFileUploadResponse updateFile(CategoryFileUpdateRequest request, boolean isBanner);
    CategoryInfoUpdateResponse updateCategoryInfo(CategoryInfoUpdateRequest request, long id);
    void softDeleteCategory(long id);
    void hardDeleteCategory(long id);
    void activateCategory(long id);
    CategoryNode getCategoryHierarchy(String name);
    CategoryNode getOnlyActiveCategoryHierarchy(String name);
    List<CategoryNode> getOnlyActiveRootCategories();
    List<CategoryNode> getCategoriesByLevel(String level);
}
