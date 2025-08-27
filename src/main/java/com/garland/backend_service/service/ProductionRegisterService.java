package com.garland.backend_service.service;

import com.garland.backend_service.model.ProductionRegister;
import com.garland.backend_service.model.Weight;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ProductionRegisterService {

    private final Sheets sheetsService;
    @Value("${google.sheets.spreadsheet-id}")
    private String spreadsheetId;

    /*      sheetName -> Production Register     */
    public ProductionRegisterService(Sheets sheetsService) {
        this.sheetsService = sheetsService;
    }


    /**
     * Get All data from Production Register Sheet
     *
     */


    public void addNewProductionRegister(String sheetName, ProductionRegister productionRegister) throws IOException {
        // Get last row
        List<List<Object>> values = sheetsService.spreadsheets().values()
                .get(spreadsheetId, sheetName + "!A:A")
                .execute()
                .getValues();

        int newId = 1; // Default for empty sheet
        if (values != null && !values.isEmpty()) {
            String lastValue = values.get(values.size() - 1).get(0).toString();
            try {
                newId = Integer.parseInt(lastValue) + 1;
            } catch (NumberFormatException e) {
                newId = values.size() + 1; // fallback
            }
        }
        productionRegister.setId(newId);

        ValueRange appendBody = new ValueRange()
                .setValues(Arrays.asList(
                        Arrays.asList(
                                newId,
                                productionRegister.getStrBarcode(),
                                productionRegister.getInsertDate(),
                                productionRegister.getQuantity(),
                                productionRegister.getLineName(),
                                productionRegister.getInsertBy(),
                                productionRegister.getProductName(),
                                productionRegister.getItemCode(),
                                productionRegister.getTotalWeight(),
                                productionRegister.getBarcode()
                        )
                ));

        sheetsService.spreadsheets().values()
                .append(spreadsheetId, sheetName + "!A:J", appendBody)
                .setValueInputOption("RAW")
                .execute();
    }

}
