package com.inhostudios.visionapitester.DataExtractionModel;


public class RecipeQuery {
    private String recipeAppID = "&app_id=ab5047e9";
    private String recipeAppKey = "&app_key=391d21268595ea4650e3d0f9039af88f";

    private String q; // query text
    private String appID = recipeAppID;     // TESTED
    private String appKey = recipeAppKey;   // TESTED
    private String from = "&from=0";        // TESTED
    private String to = "&to=3";            // TESTED
    private String diet = "";               // TESTED
    private String calories = "";           // TESTED
    private String time = "";               // TESTED
    private String exluded = "";            // TESTED

    public RecipeQuery(String oneKeyWord){
        this.setQ(oneKeyWord);
    }


    public void setQ(String q) {
        this.q = "q=" + q;
    }


    public void setDiet(String diet) {
        this.diet = "&diet=" + diet;
    }

    public void setCalories(int minCal, int maxCal) {
        this.calories = "&calories=" + minCal + "-" + maxCal;
    }

    public void setTime(int min) {
        this.time = "&time=" + min + "%2B";
    }

    public void setTime(int min, int max) {
        this.time = "&time=" + min + "-" + max;
    }

    public void setExluded(String excludeFood) {
        this.exluded = "&excluded=" + excludeFood;
    }

    public String toURL() {
        String testing = "https://api.edamam.com/search?q=chicken&app_id=ab5047e9" +
                "&app_key=391d21268595ea4650e3d0f9039af88f&from=0&to=3&health=alcohol-free&calories=591-722";


        String result = "https://api.edamam.com/search?" + q + appID +
                appKey + from + to + "&health=alcohol-free" + calories + time + exluded + diet;
        return result;
    }

}