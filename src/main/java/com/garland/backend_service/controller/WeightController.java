package com.garland.backend_service.controller;

import com.garland.backend_service.service.ProductListService;
import com.garland.backend_service.service.WeightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class WeightController {
    final WeightService weightService;

    public WeightController(WeightService weightService) {
        this.weightService = weightService;
    }

    /**
     * For Weight Sheet
     */
    @PostMapping("/weight")
    public ResponseEntity<Map<String, Object>> weight(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        System.out.println(request.get("Hello From WeightController"));
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



                if ("getWeight".equalsIgnoreCase(query)) {
                    result = weightService.getWeight(sheetName);

                    response.put("isSuccess", true);
                    response.put("data", result);
                }else if("getWeightByRowLabel".equalsIgnoreCase(query)){
                    String rowLabel = request.get("rowLabel");
                    result = weightService.getWeightByRowLabel(sheetName, rowLabel);
                    response.put("isSuccess", true);
                    response.put("data", result);
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
