package com.training;

import java.util.ArrayList;

public class Room {

    private String roomName;
    private int roomSpace;
    private int furnitureSquare;
    private int windowQuantity;
    private int light;
    private ArrayList<Item> furniture = new ArrayList();
    private ArrayList<LightBulb> lightBulbs = new ArrayList();

    //минимальное значение допустимой освещённости
    private static final int MIN_LIGHT = 300;
    //максимальное значение допустимой освещённости
    private static final int MAX_LIGHT = 4000;
    //максимальный процент от всей площади, который может занимать мебель в помещении
    private static final double MAX_FILLED_WITH_FURNITURE = 0.7;
    //освещённость одного окна
    private static final int WINDOW_LIGHT = 700;
    //5 окон - максимальное их количество, при котором не будет превышено допустимое значение освещённости
    private static final int MAX_WINDOW_NUMBER = 5;
    //при создании помещения должно быть установлено хотя бы 1 окно для достижения мин. допустимого значение освещённости
    private static final int MIN_WINDOW_NUMBER = 1;
    //площадь комнаты устанавливается по умолчанию при задании нулевого/отрицательного значения
    private static final int DEFAULT_ROOM_SIZE = 7;

    public Room(String roomName, int roomSquare, int windowQuantity)  {
        this.roomName = roomName;
        if (roomSquare <= 0){
            System.out.println("Room area must be a positive number. It will be set to 7 m^2 by default. You can change it.");
            this.roomSpace = DEFAULT_ROOM_SIZE;
        }else {
            this.roomSpace = roomSquare;
        }
        if (windowQuantity > MAX_WINDOW_NUMBER){
            try {
                throw new IlluminanceTooMuchException("An attempt to add "+windowQuantity+" windows has led to the lightness excess. " +
                        "The maximum amount of windows (5) will be set.");
            } catch (IlluminanceTooMuchException e) {
                System.out.println(e.getMessage());
                this.windowQuantity = MAX_WINDOW_NUMBER;
                light = MAX_WINDOW_NUMBER * WINDOW_LIGHT;
            }
        } else if (windowQuantity < MIN_WINDOW_NUMBER){
            System.out.println("There must be at least one window at the room, so that room could have enough light. " +
                    "You can remove it after adding lightbulbs (at least 300 lx in sum are required).");
            this.windowQuantity = MIN_WINDOW_NUMBER;
            light = MIN_WINDOW_NUMBER * WINDOW_LIGHT;
        }else {
            this.windowQuantity = windowQuantity;
            light = windowQuantity * WINDOW_LIGHT;
        }
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getRoomSquare() {
        return roomSpace;
    }

    public void setRoomSquare(int roomSquare) {
        //достаточно ли будет новой площади, чтобы мебель не занимала больше 70%
        if (roomSquare < getFurnitureSquare()/MAX_FILLED_WITH_FURNITURE){
            try {
                throw new SpaceUsageTooMuchException("Setting room area to "+roomSquare+" m^2 would lead to excess of " +
                        "admissible space covered with furniture. Room area won't be change.");
            } catch (SpaceUsageTooMuchException e) {
                System.out.println(e.getMessage());
            }
        }else if (roomSquare > 0 ){
            this.roomSpace = roomSquare;
        } else {
            System.out.println("Room area must be a positive number.");
        }
    }

    public int getWindowQuantity() {
        return windowQuantity;
    }

    public void setWindowQuantity(int windowQuantity) {
        int newLight = getBulbsLight() + windowQuantity * WINDOW_LIGHT;
        if (newLight > MAX_LIGHT){
            try {
                throw new IlluminanceTooMuchException("Adding "+windowQuantity+" windows would lead to the lightness excess. " +
                        "Try to set less windows or remove some lightbulbs.");
            } catch (IlluminanceTooMuchException e) {
                System.out.println(e.getMessage());
            }
        } else if (newLight < MIN_LIGHT){
            System.out.println("There is no enough lightness in the room with "+windowQuantity+" windows. " +
                    "You can add some lightbulbs and try to reset windows again.");
        }else {
            this.windowQuantity = windowQuantity;
            light = newLight;
        }
    }

    //суммарная освещённость лампочек
    public int getBulbsLight() {
        int bulbsLight = 0;
        for (LightBulb lightBulb : lightBulbs){
            bulbsLight += lightBulb.getLight();
        }
        return bulbsLight;
    }

    public int getFurnitureSquare() {
        return furnitureSquare;
    }

    public int getLight() {
        return light;
    }

    public void add(Item... items){
        for (Item item : items){
            furnitureSquare += item.getSquare();
            if (furnitureSquare < roomSpace*MAX_FILLED_WITH_FURNITURE){
                furniture.add(item);
            }else {
                try {
                    throw new SpaceUsageTooMuchException("Adding "+item.getSquare()+" m^2 "+item.getItemName()+" would lead to excess " +
                            "of 70% of room space occupied with furniture. You can increase area of the room and try to add it again.");
                } catch (SpaceUsageTooMuchException e) {
                    System.out.println(e.getMessage());
                }
                furnitureSquare -= item.getSquare();
            }
        }
    }

    public void add(LightBulb... bulbs){
        for (LightBulb bulb : bulbs){
            light += bulb.getLight();
            if (light < MAX_LIGHT){
                lightBulbs.add(bulb);
            }else {
                try {
                    throw new IlluminanceTooMuchException("Adding lightbulb with "+bulb.getLight()+" lx would lead to " +
                            "the lightness excess. Not added.");
                } catch (IlluminanceTooMuchException e) {
                    System.out.println(e.getMessage());
                }
                light -= bulb.getLight();
            }
        }
    }

    @Override
    public String toString() {
        //площадь, не занятая мебелью
        int freeSpace = roomSpace-furnitureSquare;
        //свободная площадь в процентах
        int freeProcent = freeSpace*100/roomSpace;
        //предварительный подсчёт освещённости
        StringBuilder lightness = new StringBuilder();
        if (windowQuantity>0){
            lightness.append(windowQuantity+" window(s) 700 lx each,");
        }
        if (lightBulbs.size()>0){
            lightness.append(" lightbulbs:");
            for (LightBulb bulb : lightBulbs){
                lightness.append(" "+bulb.getLight()+",");
            }
            lightness.deleteCharAt(lightness.lastIndexOf(","));
        }else {
            lightness.deleteCharAt(lightness.lastIndexOf(","));
        }

        StringBuilder roomInfo = new StringBuilder(" Room: "+roomName + '\n' +"  Light = "+getLight()+" lx ("+lightness+")"+ '\n' +
                "  Square = " + getRoomSquare() +" m^2 (free space: "+freeSpace+" m^2 or "+freeProcent+"%)");
        if (getFurniture().size() > 0){
            roomInfo.append('\n'+"  Furniture:"+'\n');
            for (Item item : getFurniture()){
                roomInfo.append("   "+item.getItemName()+" ("+item.getItemInfo()+")"+'\n');
            }
        }else {
            roomInfo.append('\n'+"  Without furniture"+'\n');
        }
        return String.valueOf(roomInfo);
    }


    public ArrayList<Item> getFurniture() {
        return furniture;
    }

}
