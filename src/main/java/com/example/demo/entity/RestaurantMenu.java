package com.example.demo.entity;

public class RestaurantMenu {
    private int menuID;
    private String menuName;
    private double price;
    
    public RestaurantMenu(int menuID,String menuName,double price){
        this.menuID = menuID;
        this.menuName = menuName;
        this.price = price;
    }
    public int getMenuID(){
        return menuID;
    }
    public String getMenuName(){
        return menuName;
    }
    public double getPrice(){
        return price;
    }
}
