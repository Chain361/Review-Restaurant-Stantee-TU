package com.example.demo.entity;

public class RestaurantHasMenu {
    private int menuID;
    private int placeID;
    
    public RestaurantHasMenu(int menuID,int placeID){
        this.menuID = menuID;
        this.placeID = placeID;
    }
    public int getMenuID(){
        return menuID;
    }
    public int getPlaceID(){
        return placeID;
    }
}
