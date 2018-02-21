package com.training;

public class MainApp {

    public static void main(String[] args){

        Building building = new Building("bsuir");
        building.addRoom("213", 13, 3);
        building.getRoom("213").add(new LightBulb(300), new LightBulb(300));
        building.getRoom("213").add(new Armchair("Royal",1));
        building.getRoom("213").add(new Armchair("Butterfly",1, "velours"));
        building.getRoom("213").add(new Sofa("Laguna",2));
        building.getRoom("213").add(new Table("IKEA",1, "wood"));

        building.describe();
    }
}
