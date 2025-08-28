package com.garland.backend_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garland.backend_service.model.ProductionRegister;
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
    public ResponseEntity<Map<String, Object>> productionRegister(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        System.out.println(request.get("Hello From ProductionRegisterController"));
        try {
            String sheetName = (String) request.get("sheetName");
            String query = (String) request.get("query");

            System.out.println("SheetName ->"+sheetName);
            System.out.println("Query ->"+query);


            if (sheetName == null || sheetName.isEmpty()) {
                response.put("isSuccess", false);
                response.put("message", "Sheet name is required");
            } else if (query == null || query.isEmpty()) {
                response.put("isSuccess", false);
                response.put("message", "Query is required");
            } else {
                Object result;


                if ("addNewProductionRegister".equalsIgnoreCase(query)) {
                    ObjectMapper mapper = new ObjectMapper();

                    ProductionRegister productionRegister  = mapper.convertValue(request.get("data"), ProductionRegister.class);
                    productionRegisterService.addNewProductionRegister(sheetName, productionRegister);
                    response.put("isSuccess", true);
                    response.put("data", productionRegister);
                }else {
                    response.put("isSuccess", false);
                    response.put("message", "Invalid query! ");
                }


            }
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("isSuccess", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

}
