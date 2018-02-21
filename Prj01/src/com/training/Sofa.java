package com.training;

public class Sofa extends Item{

    private String material = "leather";

    public Sofa(String name, int square){
        super(name, square);
        setMaterial(this.material);
    }

    public Sofa(String name, int square, String material){
        super(name, square);
        setMaterial(material);
    }

    @Override
    public String getItemName() {
        return "sofa "+getName();
    }

}
