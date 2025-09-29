package com.garland.backend_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garland.backend_service.model.ProductionRegister;
import com.garland.backend_service.model.RawMaterialRegister;
import com.garland.backend_service.model.RequestBean;
import com.garland.backend_service.model.ResponseBody;
import com.garland.backend_service.service.ProductionRegisterService;
import com.garland.backend_service.service.RawMaterialRegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RawMaterialRegisterController {
    final RawMaterialRegisterService rawMaterialRegisterService;

    public RawMaterialRegisterController(RawMaterialRegisterService rawMaterialRegisterService) {
        this.rawMaterialRegisterService = rawMaterialRegisterService;
    }

    /**
     * For Raw Material Register Sheet
     */
    @PostMapping("/rawMaterialRegister")
    public ResponseEntity<ResponseBody> rawMaterialRegister(@RequestBody RequestBean requestBean) {
        ResponseBody responseBody = new ResponseBody();
        System.out.println("Hello From RawMaterialRegisterController");
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

                if ("addNewRawMaterialRegister".equalsIgnoreCase(query)) {
                    ObjectMapper mapper = new ObjectMapper();
                    Object data = requestBean.getData();
                    RawMaterialRegister rawMaterialRegister = mapper.convertValue(data, RawMaterialRegister.class);
                    rawMaterialRegisterService.addNewRawMaterialRegister(sheetName, rawMaterialRegister);
                    responseBody.setSuccess(true);
                    responseBody.setData(rawMaterialRegister);
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
