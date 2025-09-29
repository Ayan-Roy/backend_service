package com.garland.backend_service.service;

import com.garland.backend_service.model.ProductionRegister;
import com.garland.backend_service.model.RawMaterialRegister;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class RawMaterialRegisterService {

    private final Sheets sheetsService;
    @Value("${google.sheets.spreadsheet-id}")
    private String spreadsheetId;

    /*      sheetName -> Production Register     */
    public RawMaterialRegisterService(Sheets sheetsService) {
        this.sheetsService = sheetsService;
    }


    /**
     * Get All data from Raw Material Register Sheet
     */


    public void addNewRawMaterialRegister(String sheetName, RawMaterialRegister rawMaterialRegister) throws IOException {
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
        rawMaterialRegister.setId(newId);

        ValueRange appendBody = new ValueRange()
                .setValues(Arrays.asList(
                        Arrays.asList(
                                newId,
                                safeValue(rawMaterialRegister.getStrBarcode()),
                                safeValue(rawMaterialRegister.getInsertDate()),
                                safeValue(rawMaterialRegister.getQuantity()),
                                safeValue(rawMaterialRegister.getWeight()),
                                safeValue(rawMaterialRegister.getLineName()),
                                safeValue(rawMaterialRegister.getInsertBy()),
                                safeValue(rawMaterialRegister.getItemCode()),
                                safeValue(rawMaterialRegister.getWarehouse())
                        )
                ));

        sheetsService.spreadsheets().values()
                .append(spreadsheetId, sheetName + "!A:I", appendBody)
                .setValueInputOption("RAW")
                .execute();
    }

    private static String safeValue(Object value) {
        return value == null ? "" : String.valueOf(value);
    }

}
