package com.garland.backend_service.model;

public class ProductList {

    private String productId;      // Pr0duc3#101
    private String packLine;       // Pack Line
    private String itemCode;       // Item Code
    private String description;    // Description
    private String warehouse;      // Warehouse
    private String date;           // Date
    private String laborRate;      // Labor Rate
    private String laborCost;      // Labor Cost
    private int laborHours;        // Labor Hours
    private String packoutStatus;  // Packout Status
    private String barcode;        // Barcode
    private String quantity;       // Quantity
    private String lot;            // Lot
    private String filter;         // Filter
    private String line;           // Line

    public ProductList() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getPackLine() {
        return packLine;
    }

    public void setPackLine(String packLine) {
        this.packLine = packLine;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLaborRate() {
        return laborRate;
    }

    public void setLaborRate(String laborRate) {
        this.laborRate = laborRate;
    }

    public String getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(String laborCost) {
        this.laborCost = laborCost;
    }

    public int getLaborHours() {
        return laborHours;
    }

    public void setLaborHours(int laborHours) {
        this.laborHours = laborHours;
    }

    public String getPackoutStatus() {
        return packoutStatus;
    }

    public void setPackoutStatus(String packoutStatus) {
        this.packoutStatus = packoutStatus;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
