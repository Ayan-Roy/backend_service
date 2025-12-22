package com.garland.backend_service.service;

import com.garland.backend_service.model.PoToReceive;
import com.garland.backend_service.model.ProductList;
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
public class PoToReceiveService {

    private final Sheets sheetsService;

    @Value("${google.sheets.spreadsheet-id}")
    private String spreadsheetId;

    public PoToReceiveService(Sheets sheetsService) {
        this.sheetsService = sheetsService;
    }


    public List<PoToReceive> getPoToReceiveList(String sheetName) throws IOException {
        System.out.println("Getting ProductList for sheet " + sheetName);
        String range = sheetName + "!A1:P";
        List<List<Object>> values = sheetsService.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute()
                .getValues();

        if (values == null || values.size() <= 1) {
            return Collections.emptyList();
        }

        // skip header row
        values.remove(0);

        List<PoToReceive> poToReceiveList = new ArrayList<>();

        for (List<Object> row : values) {
            poToReceiveList.add(mapRowToProductBean(row));
        }

        System.out.println("List Size -> " + poToReceiveList.size());
        return poToReceiveList;
    }

    public List<PoToReceive> getPoToReceiveListByDate(String sheetName, String date, String warehouse) throws IOException {
        System.out.println("Getting ProductList for sheet " + sheetName);
        String range = sheetName + "!A1:P";
        List<List<Object>> values = sheetsService.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute()
                .getValues();

        if (values == null || values.size() <= 1) {
            return Collections.emptyList();
        }

        // skip header row
        values.remove(0);

        List<PoToReceive> poToReceiveList = new ArrayList<>();

        for (List<Object> row : values) {
            PoToReceive poToReceive = mapRowToProductBean(row);
            if (poToReceive != null && poToReceive.getReceiveDate() != null && poToReceive.getReceiveDate().equals(date)
                    && poToReceive.getWarehouse() != null && poToReceive.getWarehouse().equals(warehouse)) {
                poToReceiveList.add(poToReceive);
            }
        }

        System.out.println("List Size -> " + poToReceiveList.size());
        return poToReceiveList;
    }


    private PoToReceive mapRowToProductBean(List<Object> row) {

        PoToReceive poToReceive = new PoToReceive();

        poToReceive.setPoNumber(row.size() > 0 ? row.get(0).toString() : "");
        poToReceive.setPoLine(row.size() > 1 ? row.get(1).toString() : "");
        poToReceive.setVendorId(row.size() > 2 ? row.get(2).toString() : "");
        poToReceive.setVendorName(row.size() > 3 ? row.get(3).toString() : "");
        poToReceive.setReceiveDate(row.size() > 4 ? row.get(4).toString() : "");
        poToReceive.setItemCode(row.size() > 5 ? row.get(5).toString() : "");
        poToReceive.setItemDescription(row.size() > 6 ? row.get(6).toString() : "");
        poToReceive.setPurchaseQuantity(row.size() > 7 ? row.get(7).toString() : "");
        poToReceive.setUOM(row.size() > 8 ? row.get(8).toString() : "");


        poToReceive.setContainerTrackId(row.size() > 9 ? row.get(9).toString() : "");
        poToReceive.setWarehouse(row.size() > 10 ? row.get(10).toString() : "");
        poToReceive.setLot1(row.size() > 11 ? row.get(11).toString() : "");
        poToReceive.setType(row.size() > 12 ? row.get(12).toString() : "");
        poToReceive.setLot3(row.size() > 13 ? row.get(13).toString() : "");
        poToReceive.setQuantity(row.size() > 14 ? row.get(14).toString() : "");
        poToReceive.setWeight(row.size() > 15 ? row.get(15).toString() : "");


        return poToReceive;
    }

}
