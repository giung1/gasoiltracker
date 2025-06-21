package com.gasoiltracker.model;
import java.time.LocalDate;


public class GasStationBuy {
    private LocalDate date;
    private int quantity;
    private String receipt;

    public GasStationBuy(LocalDate date, int quantity, String receipt) {
        this.date = date;
        this.quantity = quantity;
        this.receipt = receipt;
    }

    public GasStationBuy(){}

    public LocalDate getDate() {
        return date;
    }

    public int getQuantity() {
        return quantity;
    }
    public String getReceipt() {
        return receipt;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }


}