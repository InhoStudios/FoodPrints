package model;

import java.util.HashMap;

public class FoodItem {

    private String name;
    private Nutrition nutrition;
    private HashMap<String, Recipe> recipes = new HashMap<>();

    public FoodItem() {

    }
}
