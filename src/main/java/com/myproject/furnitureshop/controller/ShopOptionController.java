package com.myproject.furnitureshop.controller;

import com.myproject.furnitureshop.dto.request.ShopOptionCreationRequest;
import com.myproject.furnitureshop.dto.request.ShopOptionUpdateRequest;
import com.myproject.furnitureshop.dto.response.ShopOptionCreationResponse;
import com.myproject.furnitureshop.dto.response.ShopOptionResponse;
import com.myproject.furnitureshop.dto.response.SuccessResponse;
import com.myproject.furnitureshop.ratelimit.RateLimit;
import com.myproject.furnitureshop.service.ShopOptionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shop-options")
public class ShopOptionController {
    private final ShopOptionService shopOptionService;

    public ShopOptionController(ShopOptionService shopOptionService) {
        this.shopOptionService = shopOptionService;
    }

    @RateLimit(requests = 50)
    @PostMapping
    public ResponseEntity<SuccessResponse<ShopOptionCreationResponse>> createShopOption
            (@Valid @RequestBody ShopOptionCreationRequest request) {
        SuccessResponse<ShopOptionCreationResponse> response =
                SuccessResponse.of("Create option successfully.", this.shopOptionService.createOption(request));

        return ResponseEntity.ok().body(response);
    }

    @RateLimit
    @GetMapping
    public ResponseEntity<SuccessResponse<List<ShopOptionResponse>>> getShopOptions() {
        return ResponseEntity.ok()
                .body(SuccessResponse.of("Successfully.", this.shopOptionService.getShopOptions()));
    }

    @RateLimit
    @GetMapping("{id}")
    public ResponseEntity<?> getShopOptionDetail(@PathVariable long id) {
        return ResponseEntity.ok()
                .body(SuccessResponse.of("Successfully.", this.shopOptionService.getShopOptionDetail(id)));
    }

    @RateLimit(requests = 50)
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateShopOption(@PathVariable long id, @Valid @RequestBody ShopOptionUpdateRequest request) {
        return ResponseEntity.ok()
                .body(SuccessResponse.of("Update shop option successfully.", this.shopOptionService.updateOption(id, request)));
    }
}
