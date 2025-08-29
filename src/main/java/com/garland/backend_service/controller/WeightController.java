package com.garland.backend_service.controller;

import com.garland.backend_service.model.RequestBean;
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
    public ResponseEntity<ResponseBody> weight(@RequestBody RequestBean requestBean) {
        ResponseBody responseBody = new ResponseBody();
        System.out.println("Hello From WeightController");
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

                if ("getWeight".equalsIgnoreCase(query)) {
                    result = weightService.getWeight(sheetName);
                    responseBody.setSuccess(true);
                    responseBody.setData(result);
                } else if ("getWeightByRowLabel".equalsIgnoreCase(query)) {

                    Map<String, Object> data = requestBean.getData();
                    String rowLabel = (String) data.get("rowLabel");

                    result = weightService.getWeightByRowLabel(sheetName, rowLabel);
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
