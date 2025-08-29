package com.garland.backend_service.controller;

import com.garland.backend_service.model.ResponseBody;
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
    public ResponseEntity<ResponseBody> weight(@RequestBody Map<String, Object> request) {
        ResponseBody responseBody = new ResponseBody();
        System.out.println(request.get("Hello From WeightController"));
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

                if ("getWeight".equalsIgnoreCase(query)) {
                    data = weightService.getWeight(sheetName);
                    responseBody.setSuccess(true);
                    responseBody.setData(data);
                } else if ("getWeightByRowLabel".equalsIgnoreCase(query)) {
                    String rowLabel = (String) request.get("rowLabel");

                    data = weightService.getWeightByRowLabel(sheetName, rowLabel);
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
