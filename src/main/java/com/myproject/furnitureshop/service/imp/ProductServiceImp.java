package com.myproject.furnitureshop.service.imp;

import com.myproject.furnitureshop.repository.ProductRepository;
import com.myproject.furnitureshop.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImp implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImp(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

}
