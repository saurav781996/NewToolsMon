package com.example.newtoolsmon.unitconvert;


public class Conversion {

    private String units;
    private String values;

    public Conversion() {
    }

    public Conversion(String units, String values) {
        this.units = units;
        this.values = values;
    }

    public String getUnits() {
        return units;
    }

    public String getValues() {
        return values;
    }
}
