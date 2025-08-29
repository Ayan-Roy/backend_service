package com.garland.backend_service.model;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class RequestBean {
    @SerializedName("query")
    private String query;
    @SerializedName("sheetName")
    private String sheetName;
    @SerializedName("data")
    private Map<String, Object> data;

    public RequestBean() {
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
