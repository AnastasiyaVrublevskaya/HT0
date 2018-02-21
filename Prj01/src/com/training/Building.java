package com.training;

import java.util.ArrayList;

public class Building {

    private String name;
    private ArrayList<Room> rooms = new ArrayList();

    public Building(String name) {
        this.name = name;
    }

    public void addRoom(String roomName, int roomSquare, int windowQuantity){
        Room someRoom = new Room(roomName, roomSquare, windowQuantity);
        rooms.add(someRoom);
    }

    public Room getRoom(String roomName){
        Room someRoom = null;
        for (Room room : rooms){
            if (room.getRoomName().equals(roomName)){
                someRoom = room;
            }
        }
        //если комнаты с данным названием не существует, она будет создана с параметрами по умолчанию
        if (someRoom == null){
            System.out.println("Room with name \""+roomName+"\" hasn't been found. It will be created with default parameters: area - 7 m^2, windows - 2.");
            someRoom = new Room(roomName, 7, 2);
            rooms.add(someRoom);
            return someRoom;
        }else {
            return someRoom;
        }
    }

    public void deleteRoom(String roomName){
        int n = 0;
        for (Room room : rooms){
            if (room.getRoomName().equals(roomName)){
                rooms.remove(room);
                n++;
            }
        }
        if (n == 0){
            System.out.println("No room with name \""+roomName+"\" has been found. There is nothing to delete.");
        }else {
            System.out.println(n+" room(s) with name \""+roomName+"\" has(have) been deleted.");
        }
    }

    public void describe(){
        System.out.println("Building: "+name);
        if (rooms.size()>0){
            for (Room room : rooms){
                System.out.println(room.toString());
            }
        } else{
            System.out.println("There are no rooms.");
        }
    }
}
