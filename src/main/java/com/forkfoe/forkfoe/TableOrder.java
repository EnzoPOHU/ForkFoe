package com.forkfoe.forkfoe;

public class TableOrder {
    private int id;
    private int bill;
    private String status;
    private int tableId;

    public TableOrder(int id, int bill, String status, int tableId) {
        this.id = id;
        this.bill = bill;
        this.status = status;
        this.tableId = tableId;
    }

    // Getters
    public int getId() { return id; }
    public int getBill() { return bill; }
    public String getStatus() { return status; }
    public int getTableId() { return tableId; }

    // Setters
    public void setBill(int bill) { this.bill = bill; }
    public void setStatus(String status) { this.status = status; }
    public void setTableId(int tableId) { this.tableId = tableId; }
}
