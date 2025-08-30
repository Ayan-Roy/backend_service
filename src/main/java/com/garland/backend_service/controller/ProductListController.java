package com.garland.backend_service.controller;

import com.garland.backend_service.model.RequestBean;
import com.garland.backend_service.model.ResponseBody;
import com.garland.backend_service.service.ProductListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
    public ResponseEntity<ResponseBody> productList(@RequestBody RequestBean requestBean) {
        ResponseBody responseBody = new ResponseBody();

        try {
            String sheetName = requestBean.getSheetName();
            String query = requestBean.getQuery();

            if (sheetName == null || sheetName.isEmpty()) {
                responseBody.setSuccess(false);
                responseBody.setMessage("Sheet name is required");
            } else if (query == null || query.isEmpty()) {
                responseBody.setSuccess(false);
                responseBody.setMessage("Query is required");
            } else {
                Object result;

                if ("getProductList".equalsIgnoreCase(query)) {
                    result = productListService.getProductList(sheetName);
                    responseBody.setSuccess(true);
                    responseBody.setData(result);
                } else if ("updateTotalQuantity".equalsIgnoreCase(query)) {
                    Map<String, Object> data = requestBean.getData();
                    String barcode = (String) data.get("barcode");
                    String itemCode = (String) data.get("itemCode");
                    String totalQuantity = (String) data.get("totalQuantity");

                    result = productListService.updateTotalQuantity(sheetName, barcode, itemCode, totalQuantity);
                    responseBody.setSuccess(true);
                    responseBody.setData(result);
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
