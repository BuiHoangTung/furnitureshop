package com.myproject.furnitureshop.service.imp;

import com.myproject.furnitureshop.dto.request.ProductCreationRequest;
import com.myproject.furnitureshop.dto.response.ProductCreationResponse;
import com.myproject.furnitureshop.repository.ProductRepository;
import com.myproject.furnitureshop.service.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImp implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImp(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PreAuthorize("hasAuthority('PRODUCT_CREATE')")
    @Override
    public ProductCreationResponse createProduct(ProductCreationRequest request) {
        return null;
    }
}
