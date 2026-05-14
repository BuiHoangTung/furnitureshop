package com.myproject.furnitureshop.service;

import com.myproject.furnitureshop.dto.request.ProductCreationRequest;
import com.myproject.furnitureshop.dto.response.ProductCreationResponse;

public interface ProductService {
    ProductCreationResponse createProduct(ProductCreationRequest request);
}
