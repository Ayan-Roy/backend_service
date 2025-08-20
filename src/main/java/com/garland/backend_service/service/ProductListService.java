package com.garland.backend_service.service;

import com.garland.backend_service.model.ProductList;
import com.google.api.services.sheets.v4.Sheets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ProductListService {

    private final Sheets sheetsService;

    @Value("${google.sheets.spreadsheet-id}")
    private String spreadsheetId;

    public ProductListService(Sheets sheetsService) {
        this.sheetsService = sheetsService;
    }

    // Raw sheet data (optional)
    public List<List<Object>> readSheet(String sheetName) throws IOException {
        String range = sheetName + "!A:O"; // Only 15 columns (A–O)
        List<List<Object>> values = sheetsService.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute()
                .getValues();

        if (values == null) return Collections.emptyList();
        return values;
    }


    // Map sheet to ProductList objects
    public List<ProductList> getProductList(String sheetName) throws IOException {
        System.out.println("Getting ProductList for sheet " + sheetName);
        String range = sheetName + "!A1:O"; // Only 15 columns
        List<List<Object>> values = sheetsService.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute()
                .getValues();

        if (values == null || values.size() <= 1) {
            return Collections.emptyList();
        }

        // skip header row
        values.remove(0);

        List<ProductList> productList = new ArrayList<>();

        for (List<Object> row : values) {
            ProductList product = new ProductList();

            product.setProductId(row.size() > 0 ? row.get(0).toString() : "");
            product.setPackLine(row.size() > 1 ? row.get(1).toString() : "");
            product.setItemCode(row.size() > 2 ? row.get(2).toString() : "");
            product.setDescription(row.size() > 3 ? row.get(3).toString() : "");
            product.setWarehouse(row.size() > 4 ? row.get(4).toString() : "");
            product.setDate(row.size() > 5 ? row.get(5).toString() : "");
            product.setLaborRate(row.size() > 6 ? row.get(6).toString() : "");
            product.setLaborCost(row.size() > 7 ? row.get(7).toString() : "");

            // Safe parsing for laborHours
            try {
                product.setLaborHours(row.size() > 8 ? Integer.parseInt(row.get(8).toString()) : 0);
            } catch (NumberFormatException e) {
                product.setLaborHours(0);
            }

            product.setPackoutStatus(row.size() > 9 ? row.get(9).toString() : "");
            product.setBarcode(row.size() > 10 ? row.get(10).toString() : "");
            product.setQuantity(row.size() > 11 ? row.get(11).toString() : "");
            product.setLot(row.size() > 12 ? row.get(12).toString() : "");
            product.setFilter(row.size() > 13 ? row.get(13).toString() : "");
            product.setLine(row.size() > 14 ? row.get(14).toString() : "");

            productList.add(product);
        }

        System.out.println("List Size -> " + productList.size());
        return productList;
    }
}
