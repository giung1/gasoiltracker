package com.gasoiltracker.model;

import java.io.Serializable;
import java.time.LocalDate;

public class GasStationFueling implements Serializable{
    private LocalDate date;
    private String driver;
    private int quantity;
    private String receipt;

    public GasStationFueling(LocalDate date, String driver, int quantity, String receipt) {
        this.date = date;
        this.driver = driver;
        this.quantity = quantity;
        this.receipt = receipt;
    }

    public GasStationFueling(String driver, int quantity, String receipt) {
        this.date = LocalDate.now(); // Default to current date
        this.driver = driver;
        this.quantity = quantity;
        this.receipt = receipt;
    }

    public GasStationFueling(){}

    public LocalDate getDate() {
        return date;
    }

    public String getDriver() {
        return driver;
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

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

}