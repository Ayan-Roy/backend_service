package com.garland.backend_service;

import com.garland.backend_service.service.GoogleSheetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class SheetController {

    private final GoogleSheetService googleSheetService;

    public SheetController(GoogleSheetService googleSheetService) {
        this.googleSheetService = googleSheetService;
    }

    @GetMapping("/read")
    public List<List<Object>> read(@RequestParam String tabName) throws IOException {
        return googleSheetService.readSheet(tabName);
    }
}
