package com.garland.backend_service.service;

import com.garland.backend_service.model.Weight;
import com.google.api.services.sheets.v4.Sheets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class WeightService {

    private final Sheets sheetsService;
    @Value("${google.sheets.spreadsheet-id}")
    private String spreadsheetId;

    /*      sheetName -> Weight     */
    public WeightService(Sheets sheetsService) {
        this.sheetsService = sheetsService;
    }


    /**
     * Get All data from Weight Sheet
     *
     */
    public List<Weight> getWeight(String sheetName) throws IOException {
        System.out.println("Getting weight for sheet " + sheetName);
        String range = sheetName + "!A1:J";
        List<List<Object>> values = sheetsService.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute()
                .getValues();

        if (values == null || values.size() <= 1) {
            return Collections.emptyList();
        }

        // skip header row
        values.remove(0);

        List<Weight> weightList = new ArrayList<>();

        for (List<Object> row : values) {
            Weight weight = new Weight();

            weight.setRowLabel(row.size() > 0 ? row.get(0).toString() : "");
            weight.setItemDescription(row.size() > 1 ? row.get(1).toString() : "");
            weight.setGsg(row.size() > 2 ? row.get(2).toString() : "");
            weight.setGroup(row.size() > 3 ? row.get(3).toString() : "");
            weight.setGroupDesc(row.size() > 4 ? row.get(4).toString() : "");
            weight.setGroupName(row.size() > 5 ? row.get(5).toString() : "");
            weight.setGroupName2(row.size() > 6 ? row.get(6).toString() : "");
            weight.setPlant(row.size() > 7 ? row.get(7).toString() : "");
            weight.setLine(row.size() > 8 ? row.get(8).toString() : "");
            weight.setWeight(row.size() > 9 ? row.get(9).toString() : "0");

            weightList.add(weight);
        }

        System.out.println("List Size -> " + weightList.size());
        return weightList;
    }


    /**
     * Get Weight by Row Label( Item Code  )
     * rowLabel = Item Code
     *
     */
    public List<Weight> getWeightByRowLabel(String sheetName, String rowLabel) throws IOException {
        System.out.println("Getting weight for sheet " + sheetName + " where RowLabel = " + rowLabel);
        String range = sheetName + "!A1:J";
        List<List<Object>> values = sheetsService.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute()
                .getValues();

        if (values == null || values.size() <= 1) {
            return Collections.emptyList();
        }

        values.remove(0); // skip header

        List<Weight> weightList = new ArrayList<>();

        for (List<Object> row : values) {
            if (row.size() > 0 && row.get(0).toString().equalsIgnoreCase(rowLabel)) {
                Weight weight = new Weight();
                weight.setRowLabel(row.get(0).toString());
                weight.setItemDescription(row.size() > 1 ? row.get(1).toString() : "");
                weight.setGsg(row.size() > 2 ? row.get(2).toString() : "");
                weight.setGroup(row.size() > 3 ? row.get(3).toString() : "");
                weight.setGroupDesc(row.size() > 4 ? row.get(4).toString() : "");
                weight.setGroupName(row.size() > 5 ? row.get(5).toString() : "");
                weight.setGroupName2(row.size() > 6 ? row.get(6).toString() : "");
                weight.setPlant(row.size() > 7 ? row.get(7).toString() : "");
                weight.setLine(row.size() > 8 ? row.get(8).toString() : "");
                weight.setWeight(row.size() > 9 ? row.get(9).toString() : "0");
                weightList.add(weight);
            }
        }

        System.out.println("Filtered List Size -> " + weightList.size());
        return weightList;
    }

}
