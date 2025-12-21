package com.garland.backend_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garland.backend_service.model.PoToReceive;
import com.garland.backend_service.model.ProductionRegister;
import com.garland.backend_service.model.RequestBean;
import com.garland.backend_service.model.ResponseBody;
import com.garland.backend_service.service.PoToReceiveService;
import com.garland.backend_service.service.ProductionRegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PoToReceiveController {
    final PoToReceiveService poToReceiveService;

    public PoToReceiveController(PoToReceiveService poToReceiveService) {
        this.poToReceiveService = poToReceiveService;
    }

    /**
     * For Po to Receive Sheet
     */
    @PostMapping("/poToReceive")
    public ResponseEntity<ResponseBody> productionRegister(@RequestBody RequestBean requestBean) {
        ResponseBody responseBody = new ResponseBody();
        System.out.println("Hello From PoToReceiveController");
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

                if ("getPoToReceiveList".equalsIgnoreCase(query)) {

                    Object result = poToReceiveService.getPoToReceiveListByDate(sheetName);
                    if (result == null) {
                        responseBody.setSuccess(false);
                        responseBody.setMessage("No production register found");
                    } else {
                        responseBody.setSuccess(true);
                        responseBody.setData(result);
                    }
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
