package com.garland.backend_service.service;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
public class GoogleSheetService {

    private final Sheets sheetsService;

    @Value("${google.sheets.spreadsheet-id}")
    private String spreadsheetId;

    // Optional: restrict to allowed tabs
    private static final Set<String> ALLOWED_TABS = Set.of("Sheet1", "Expenses", "Summary", "Sheet31");


    public GoogleSheetService(Sheets sheetsService) {
        this.sheetsService = sheetsService;
    }

    public List<List<Object>> readSheet(String sheetName) throws IOException {
        String range = sheetName + "!A1:Z100";
        return sheetsService.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute()
                .getValues();
    }

}
