package com.example.servicestation.Models;

import androidx.annotation.NonNull;

public class Area {
    public String OwnerName ;
    public String Name ;
    public String Model ;
    public int Year ;
    public String Number ;
    public String VIN ;
    public int Capacity ;

    public int getCapacity() {
        return Capacity;
    }

    public int getYear() {
        return Year;
    }

    public String getModel() {
        return Model;
    }

    public String getName() {
        return Name;
    }

    public String getNumber() {
        return Number;
    }

    public String getOwnerName() {
        return OwnerName;
    }

    public String getVIN() {
        return VIN;
    }
}
