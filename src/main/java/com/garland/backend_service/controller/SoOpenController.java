package com.garland.backend_service.controller;

import com.garland.backend_service.model.RequestBean;
import com.garland.backend_service.model.ResponseBody;
import com.garland.backend_service.service.SoOpenService;
import com.garland.backend_service.service.WeightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SoOpenController {
    final SoOpenService soOpenService;

    public SoOpenController(SoOpenService soOpenService) {
        this.soOpenService = soOpenService;
    }

    /**
     * For So_Open Sheet
     */
    @PostMapping("/soOpen")
    public ResponseEntity<ResponseBody> weight(@RequestBody RequestBean requestBean) {
        ResponseBody responseBody = new ResponseBody();
        System.out.println("Hello From SoOpenController");
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

                if ("getAllSoOpenData".equalsIgnoreCase(query)) {
                    result = soOpenService.getAllSoOpenData(sheetName);
                    responseBody.setSuccess(true);
                    responseBody.setData(result);
                }else {
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
