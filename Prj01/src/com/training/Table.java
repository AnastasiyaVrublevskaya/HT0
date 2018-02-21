package com.training;

public class Table extends Item{

    private String material = "laminated chipboard";

    public Table(String name, int square){
        super(name, square);
        setMaterial(this.material);
    }

    public Table(String name, int square, String material){
        super(name, square);
        setMaterial(material);
    }

    @Override
    public String getItemName() {
        return "table "+getName();
    }

}
