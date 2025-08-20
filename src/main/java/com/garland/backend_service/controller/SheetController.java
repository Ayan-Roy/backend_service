package com.garland.backend_service.controller;

import com.garland.backend_service.service.ProductListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SheetController {

    private final ProductListService productListService;

    public SheetController(ProductListService productListService) {
        this.productListService = productListService;
    }


    /**
     * For Product List Sheet
     */
    @PostMapping("/productList")
    public ResponseEntity<Map<String, Object>> productList(@RequestBody Map<String, String> request) throws IOException {
        Map<String, Object> response = new HashMap<>();

        try {
            String sheetName = request.get("sheetName");
            String query = request.get("query");

            if (sheetName == null || sheetName.isEmpty()) {
                response.put("isSuccess", false);
                response.put("message", "Sheet name is required");
            } else if (query == null || query.isEmpty()) {
                response.put("isSuccess", false);
                response.put("message", "Query is required");
            } else {
                Object result;
                if ("getProductList".equalsIgnoreCase(query)) {
                    result = productListService.getProductList(sheetName);
                } else {
                    result = productListService.readSheet(sheetName);
                }
                response.put("isSuccess", true);
                response.put("data", result);
            }
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(response);
        }
    }

}
