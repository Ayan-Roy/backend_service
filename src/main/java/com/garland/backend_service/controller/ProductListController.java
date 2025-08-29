package com.garland.backend_service.controller;

import com.garland.backend_service.model.ResponseBody;
import com.garland.backend_service.service.ProductListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ProductListController {

    private final ProductListService productListService;

    public ProductListController(ProductListService productListService) {
        this.productListService = productListService;
    }


    /**
     * For Product List Sheet
     */
    @PostMapping("/productList")
    public ResponseEntity<ResponseBody> productList(@RequestBody Map<String, Object> request) {
        ResponseBody responseBody = new ResponseBody();

        try {
            String sheetName = (String) request.get("sheetName");
            String query = (String) request.get("query");

            if (sheetName == null || sheetName.isEmpty()) {
                responseBody.setSuccess(false);
                responseBody.setMessage("Sheet name is required");
            } else if (query == null || query.isEmpty()) {
                responseBody.setSuccess(false);
                responseBody.setMessage("Query is required");
            } else {
                Object data;

                if ("getProductList".equalsIgnoreCase(query)) {
                    data = productListService.getProductList(sheetName);
                    responseBody.setSuccess(true);
                    responseBody.setData(data);
                } else if ("updateTotalQuantity".equalsIgnoreCase(query)) {
                    String totalQuantity = (String) request.get("totalQuantity");
                    String strBarcode = (String) request.get("barcode");
                    data = productListService.updateTotalQuantity(sheetName, strBarcode, totalQuantity);
                    responseBody.setSuccess(true);
                    responseBody.setData(data);
                } else {
                    responseBody.setSuccess(false);
                    responseBody.setMessage("Invalid query!");
                }


            }
            return ResponseEntity.ok(responseBody);

        } catch (Exception e) {
            e.printStackTrace();
            responseBody.setSuccess(false);
            responseBody.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(responseBody);
        }
    }

}
