package com.myproject.furnitureshop.service;

import com.myproject.furnitureshop.dto.request.CategoryCreationRequest;
import com.myproject.furnitureshop.dto.request.CategoryFileUpdateRequest;
import com.myproject.furnitureshop.dto.request.CategoryFileUploadRequest;
import com.myproject.furnitureshop.dto.request.CategoryInfoUpdateRequest;
import com.myproject.furnitureshop.dto.response.CategoryCreationResponse;
import com.myproject.furnitureshop.dto.response.CategoryFileUploadResponse;
import com.myproject.furnitureshop.dto.response.CategoryInfoUpdateResponse;

public interface CategoryService {
    CategoryCreationResponse createCategory(CategoryCreationRequest request);
    CategoryFileUploadResponse uploadCategoryFile(CategoryFileUploadRequest request);
    CategoryFileUploadResponse updateFile(CategoryFileUpdateRequest request, boolean isBanner);
    CategoryInfoUpdateResponse updateCategoryInfo(CategoryInfoUpdateRequest request, long id);
    void softDeleteCategory(long id);
    void hardDeleteCategory(long id);
}
