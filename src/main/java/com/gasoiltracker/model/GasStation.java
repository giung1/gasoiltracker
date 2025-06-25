package com.gasoiltracker.model;

import java.io.Serializable;
import java.util.ArrayList;

public class GasStation implements Serializable{
    private String name;
    private String locality;
    private int quantity;
    private ArrayList<GasStationFueling> fuelingList;
    private ArrayList<GasStationBuy> buyList;

    public GasStation(String name, String locality, int quantity)  {
        this.name = name.toUpperCase();
        this.locality = locality.toUpperCase();
        this.quantity = quantity;
        this.fuelingList = new ArrayList<>();
        this.buyList = new ArrayList<>();
    }

    public GasStation(){
        this.fuelingList = new ArrayList<>();
        this.buyList = new ArrayList<>();
    }

    public void addFueling(GasStationFueling fueling) {
        fuelingList.add(fueling);
        int newQuantity = this.quantity - fueling.getQuantity();
        if (!(newQuantity < 0)){
            this.quantity = newQuantity;
        }
    }

    public void removeFueling(GasStationFueling fueling){
        this.fuelingList.remove(fueling);
        this.quantity = this.quantity + fueling.getQuantity();
    }
    
    public ArrayList<GasStationFueling> getFuelingList() {
        return fuelingList;
    }

    public void addBuy(GasStationBuy buy) {
        buyList.add(buy);
        this.quantity = this.quantity + buy.getQuantity();
    }

    public void removeBuy(GasStationBuy buy){
        buyList.remove(buy);
        this.quantity = this.quantity - buy.getQuantity();
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