package com.garland.backend_service.service;

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
public class ProductListService {

    private final Sheets sheetsService;

    @Value("${google.sheets.spreadsheet-id}")
    private String spreadsheetId;

    public ProductListService(Sheets sheetsService) {
        this.sheetsService = sheetsService;
    }


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
            productList.add(mapRowToProductBean(row));
        }

        System.out.println("List Size -> " + productList.size());
        return productList;
    }


    public List<ProductList> getProductByBarcode(String sheetName, String strBarcode) throws IOException {
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
            if (row.size() > 10) {
                String barcode = row.get(10).toString();
                if (barcode != null && !barcode.isEmpty() && strBarcode.trim().endsWith(barcode.trim())){
                    productList.add(mapRowToProductBean(row));
                    return productList;
                }
            }
        }
        return productList;
    }


    public ProductList updateTotalQuantity(String sheetName, String strBarcode, String itemCode, String totalQuantity) throws IOException {
        // Step 1: Get all data from the sheet
        String range = sheetName + "!A:L"; // Read up to Column L
        ValueRange response = sheetsService.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues();

        if (values == null || values.isEmpty()) {
            throw new IOException("No data found in sheet.");
        }

        int rowIndex = -1;
        for (int i = 0; i < values.size(); i++) {
            List<Object> row = values.get(i);
            if (row.size() > 10 && row.get(10).toString().endsWith(strBarcode) &&
                    row.get(2).toString().equals(itemCode)) {
                rowIndex = i;
                break;
            }
        }

        if (rowIndex == -1) {
            throw new IOException("Product with barcode " + strBarcode + " not found.");
        }

        // Step 2: Update only column L (12th column, index starts from A=1)
        String cellRange = sheetName + "!L" + (rowIndex + 1); // Adding +1 because rows start from 1 in Sheets
        ValueRange body = new ValueRange().setValues(
                Arrays.asList(Arrays.asList(totalQuantity)) // Single cell update
        );
        sheetsService.spreadsheets().values()
                .update(spreadsheetId, cellRange, body)
                .setValueInputOption("RAW")
                .execute();

        // Step 3: Create Bean object from updated row
        List<Object> updatedRow = values.get(rowIndex);
        if (updatedRow.size() < 12) {
            while (updatedRow.size() < 12) updatedRow.add(""); // Fill empty cells
        }
        updatedRow.set(11, totalQuantity); // Update value locally too

        return mapRowToProductBean(updatedRow); // Return mapped Bean
    }


    private ProductList mapRowToProductBean(List<Object> row) {

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
        return product;
    }

}
