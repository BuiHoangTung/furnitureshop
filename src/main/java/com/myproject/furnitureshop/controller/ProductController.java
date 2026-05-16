package com.myproject.furnitureshop.controller;

import com.myproject.furnitureshop.adapter.Storage;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final Storage storage;

    public ProductController(Storage storage) {
        this.storage = storage;
    }
}
