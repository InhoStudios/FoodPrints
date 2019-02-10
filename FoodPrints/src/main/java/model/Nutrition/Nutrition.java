package model.Nutrition;

import model.FoodItem;

import java.util.HashMap;

public class Nutrition {
    private FoodItem foodItem;
    private HashMap<String, NutritionAttribute> nutritionAttributes = new HashMap<>();

    public Nutrition(FoodItem foodItem) {
        this.foodItem = foodItem;
    }

    //EFFECTS: converts string of data given containing nutrition attribute data to values and qualities of a nutrition
    //         attribute of the given name
    public void setNutritionAttributes(String data, String name) {

    }

    //EFFECTS: returns nutrition attribute with the given key
    public NutritionAttribute getAttribute(String name) {
        return new NutritionAttribute("Test", 0, "J");
    }
}
