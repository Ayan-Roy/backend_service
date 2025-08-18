package com.garland.backend_service;

import com.garland.backend_service.service.ProductListService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
public class SheetController {

    private final ProductListService productListService;

    public SheetController(ProductListService productListService) {
        this.productListService = productListService;
    }





    @PostMapping("/productList")
    public Object productList(@RequestBody Map<String, String> request) throws IOException {
        String sheetName = request.get("sheetName");
        String query = request.get("query");

        if (sheetName == null || sheetName.isEmpty()) {
            throw new IllegalArgumentException("sheetName cannot be null or empty");
        }

        if(query.equalsIgnoreCase("getProductList")){
            return productListService.getProductList(sheetName);
        }
        return productListService.readSheet(sheetName);
    }

}
