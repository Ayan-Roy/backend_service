package com.garland.backend_service;

import com.garland.backend_service.service.GoogleSheetService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class SheetController {

    private final GoogleSheetService googleSheetService;

    public SheetController(GoogleSheetService googleSheetService) {
        this.googleSheetService = googleSheetService;
    }

    @PostMapping("/read")
    public List<List<Object>> read(@RequestBody Map<String, String> request) throws IOException {
        String tabName = request.get("tabName");
        return googleSheetService.readSheet(tabName);
    }

}
