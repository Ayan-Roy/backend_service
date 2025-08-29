package com.garland.backend_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garland.backend_service.model.ProductionRegister;
import com.garland.backend_service.model.RequestBean;
import com.garland.backend_service.model.ResponseBody;
import com.garland.backend_service.service.ProductionRegisterService;
import com.garland.backend_service.service.WeightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ProductionRegisterController {
    final ProductionRegisterService productionRegisterService;

    public ProductionRegisterController(ProductionRegisterService productionRegisterService) {
        this.productionRegisterService = productionRegisterService;
    }

    /**
     * For Production Register Sheet
     */
    @PostMapping("/productionRegister")
    public ResponseEntity<ResponseBody> productionRegister(@RequestBody RequestBean requestBean) {
        ResponseBody responseBody = new ResponseBody();
        System.out.println("Hello From ProductionRegisterController");
        try {
            String sheetName = requestBean.getSheetName();
            String query = requestBean.getQuery();

            System.out.println("SheetName ->" + sheetName);
            System.out.println("Query ->" + query);


            if (sheetName == null || sheetName.isEmpty()) {
                responseBody.setSuccess(false);
                responseBody.setMessage("Sheet name is required");
            } else if (query == null || query.isEmpty()) {
                responseBody.setSuccess(false);
                responseBody.setMessage("Query is required");
            } else {

                if ("addNewProductionRegister".equalsIgnoreCase(query)) {
                    ObjectMapper mapper = new ObjectMapper();
                    Object data = requestBean.getData();
                    ProductionRegister productionRegister = mapper.convertValue(data, ProductionRegister.class);
                    productionRegisterService.addNewProductionRegister(sheetName, productionRegister);
                    responseBody.setSuccess(true);
                    responseBody.setData(productionRegister);
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
