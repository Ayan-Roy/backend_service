package com.garland.backend_service.service;

import com.garland.backend_service.model.SoOpen;
import com.garland.backend_service.model.Weight;
import com.garland.backend_service.model.WeightWithUPC;
import com.google.api.services.sheets.v4.Sheets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SoOpenService {

    private final Sheets sheetsService;
    @Value("${google.sheets.spreadsheet-id}")
    private String spreadsheetId;

    /*      sheetName -> Weight     */
    public SoOpenService(Sheets sheetsService) {
        this.sheetsService = sheetsService;
    }


    /**
     * Get All data from so_open Sheet
     *
     */
    public List<SoOpen> getAllSoOpenData(String sheetName) throws IOException {
        System.out.println("Getting Data for sheet " + sheetName);
        String range = sheetName + "!A1:O";
        List<List<Object>> values = sheetsService.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute()
                .getValues();

        if (values == null || values.size() <= 1) {
            return Collections.emptyList();
        }

        // skip header row
        values.remove(0);

        List<SoOpen> soOpenList = new ArrayList<>();

        for (List<Object> row : values) {

            SoOpen soOpen = new SoOpen();
            soOpen.setEmptyField1(row.size() > 0 && row.get(0) != null ? row.get(0).toString() : "");
            soOpen.setCompId(row.size() > 1 && row.get(1) != null ? row.get(1).toString() : "");
            soOpen.setEmptyField2(row.size() > 2 && row.get(2) != null ? row.get(2).toString() : "");
            soOpen.setLocId(row.size() > 3 && row.get(3) != null ? row.get(3).toString() : "");
            soOpen.setInvoiceNumber(row.size() > 4 && row.get(4) != null ? row.get(4).toString() : "");
            soOpen.setInvoiceLineNumber(row.size() > 5 && row.get(5) != null ? row.get(5).toString() : "");
            soOpen.setCustId(row.size() > 6 && row.get(6) != null ? row.get(6).toString() : "");
            soOpen.setCustLocId(row.size() > 7 && row.get(7) != null ? row.get(7).toString() : "");
            soOpen.setItemCode(row.size() > 8 && row.get(8) != null ? row.get(8).toString() : "");
            soOpen.setItemDescription(row.size() > 9 && row.get(9) != null ? row.get(9).toString() : "");
            soOpen.setOrderQuantity(row.size() > 10 && row.get(10) != null ? row.get(10).toString() : "");
            soOpen.setShipQuantity(row.size() > 11 && row.get(11) != null ? row.get(11).toString() : "");
            soOpen.setOrderUomId(row.size() > 12 && row.get(12) != null ? row.get(12).toString() : "");
            soOpen.setShipWeight(row.size() > 13 && row.get(13) != null ? row.get(13).toString() : "");
            soOpen.setUnitPrice(row.size() > 14 && row.get(14) != null ? row.get(14).toString() : "");

            soOpenList.add(soOpen);

        }

        System.out.println("List Size -> " + soOpenList.size());
        return soOpenList;
    }



}
