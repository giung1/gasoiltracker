package com.gasoiltracker.model;

import java.util.ArrayList;

public class GasStation {
    private String name;
    private String locality;
    private int quantity;
    private ArrayList<GasStationFueling> fuelingList;
    private ArrayList<GasStationBuy> buyList;

    public GasStation(String name, String locality, int quantity) {
        this.name = name;
        this.locality = locality;
        this.quantity = quantity;
        this.fuelingList = new ArrayList<>();
        this.buyList = new ArrayList<>();
    }

    public GasStation(){}

    public void addFueling(GasStationFueling fueling) {
        fuelingList.add(fueling);
    }

    public ArrayList<GasStationFueling> getFuelingList() {
        return fuelingList;
    }

    public void addBuy(GasStationBuy buy) {
        buyList.add(buy);
    }

    public ArrayList<GasStationBuy> getBuyList() {
        return buyList;
    }

    public String getName() {
        return name;
    }
    public String getLocality() {
        return locality;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setName(String name) {
        this.name = name;
    
    }
    public void setLocality(String locality) {
        this.locality = locality;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}