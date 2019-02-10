package model;

import model.Nutrition.Nutrition;

import java.util.HashMap;

public class FoodItem {

    private String name;
    private Nutrition nutrition;
    private HashMap<String, Recipes> recipes = new HashMap<>();

    public FoodItem(String name) {
        this.name = name;
    }
}
