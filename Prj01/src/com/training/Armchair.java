package com.training;

public class Armchair extends Item {

    private String material = "leather";

    public Armchair(String name, int square){
        super(name, square);
        setMaterial(this.material);
    }

    public Armchair(String name, int square, String material){
        super(name, square);
        setMaterial(material);
    }

    @Override
    public String getItemName() {
        return "armchair "+getName();
    }

}
