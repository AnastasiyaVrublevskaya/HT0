package com.training;

//класс Item наследуют классы, представляющие произвольные предметы в помещении
public abstract class Item {

    private int square;
    private String name;
    private String material;

    public Item(String name, int square){
        this.name = name;
        this.square = square;
    }

    public int getSquare() {
        return square;
    }

    //задание площади возможно только при добавлении предмета в помещение
    //последующее изменение площади предмета не предусмотрено
    //(нет проверки, что не будет превышено значение в 70%)
    private void setSquare(int square) {

        this.square = square;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    //метод, необходимый для переопределения подклассами
    public abstract String getItemName();

    //метод, общий для всех подклассов
    public String getItemInfo(){
        return String.valueOf("square: "+getSquare()+" m^2, material: "+getMaterial());

    };
}
