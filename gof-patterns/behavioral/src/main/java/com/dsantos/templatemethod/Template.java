package com.dsantos.templatemethod;

public class Template {

    public static void main(String[] args) {

        HouseTemplate houseType = new WoodenHouse();

        //using template method
        houseType.buildHouse();
        System.out.println("************");

        HouseTemplate glassHouse = new GlassHouse();

        glassHouse.buildHouse();
    }
}
