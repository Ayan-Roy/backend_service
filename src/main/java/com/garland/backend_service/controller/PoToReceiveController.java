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
import java.util.Map;

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
                Object result = null;
                if ("getPoToReceiveList".equalsIgnoreCase(query)) {

                    result = poToReceiveService.getPoToReceiveList(sheetName);
                    if (result == null) {
                        responseBody.setSuccess(false);
                        responseBody.setMessage("No production register found");
                    } else {
                        responseBody.setSuccess(true);
                        responseBody.setData(result);
                    }
                } else if ("getPoToReceiveListByDate".equalsIgnoreCase(query)) {
                    Map<String, Object> data = requestBean.getData();
                    if (data != null) {
                        String date = (String) data.get("date");
                        String warehouse = (String) data.get("warehouse");
                        if (date == null) {
                            responseBody.setSuccess(false);
                            responseBody.setMessage("Date is required");
                        } else if (warehouse == null) {
                            responseBody.setSuccess(false);
                            responseBody.setMessage("Warehouse is required");
                        } else {
                            result = poToReceiveService.getPoToReceiveListByDate(sheetName, date, warehouse);
                            responseBody.setSuccess(true);
                            responseBody.setData(result);
                        }
                    } else {
                        responseBody.setSuccess(false);
                        responseBody.setMessage("Invalid Data!");
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
