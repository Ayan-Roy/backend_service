package com.garland.backend_service.model;

public class PoToReceive {

    private String PoNumber;
    private String PoLine;
    private String VendorId;
    private String VendorName;
    private String ReceiveDate;
    private String ItemCode;
    private String ItemDescription;
    private String PurchaseQuantity;
    private String UOM;
    private String ContainerTrackId;
    private String Warehouse;
    private String Lot1;
    private String type;
    private String Lot3;
    private String Quantity;
    private String Weight;

    public PoToReceive() {
    }

    public String getPoNumber() {
        return PoNumber;
    }

    public void setPoNumber(String poNumber) {
        PoNumber = poNumber;
    }

    public String getPoLine() {
        return PoLine;
    }

    public void setPoLine(String poLine) {
        PoLine = poLine;
    }

    public String getVendorId() {
        return VendorId;
    }

    public void setVendorId(String vendorId) {
        VendorId = vendorId;
    }

    public String getVendorName() {
        return VendorName;
    }

    public void setVendorName(String vendorName) {
        VendorName = vendorName;
    }

    public String getReceiveDate() {
        return ReceiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        ReceiveDate = receiveDate;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getItemDescription() {
        return ItemDescription;
    }

    public void setItemDescription(String itemDescription) {
        ItemDescription = itemDescription;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getUOM() {
        return UOM;
    }

    public void setUOM(String UOM) {
        this.UOM = UOM;
    }

    public String getContainerTrackId() {
        return ContainerTrackId;
    }

    public void setContainerTrackId(String containerTrackId) {
        ContainerTrackId = containerTrackId;
    }

    public String getWarehouse() {
        return Warehouse;
    }

    public void setWarehouse(String warehouse) {
        Warehouse = warehouse;
    }

    public String getLot1() {
        return Lot1;
    }

    public void setLot1(String lot1) {
        Lot1 = lot1;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLot3() {
        return Lot3;
    }

    public void setLot3(String lot3) {
        Lot3 = lot3;
    }

    public String getPurchaseQuantity() {
        return PurchaseQuantity;
    }

    public void setPurchaseQuantity(String purchaseQuantity) {
        PurchaseQuantity = purchaseQuantity;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }
}
