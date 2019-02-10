package com.inhostudios.visionapitester.DataExtractionModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RecipeManager {

    private ArrayList<Recipe> recipeList;

    // need to pass through filter variables to the recipe manager in the constructor
    /*
    mincals
    maxcals
    mintime
    maxtime
    diettype (which is an enumerator)
     */
    private Double minCals, maxCals = 1000000.0;
    private Integer minTime, maxTime = 1000000;
    private String dietType;
    public RecipeManager(List<Object> recipeJsons){
        refreshRecipes(recipeJsons);
    }

    public RecipeManager(List<Object> recipeJsons, Optional<Double> minCals, Optional<Double> maxCals, Optional<Integer> minTime, Optional<Integer> maxTime, Optional<String> dietType){
        this.minCals = minCals.isPresent() ? minCals.get() : 0.0;
        this.maxCals = maxCals.isPresent() ? maxCals.get() : 1000000.0;
        this.minTime = minTime.isPresent() ? minTime.get() : 0;
        this.maxTime = maxTime.isPresent() ? maxTime.get() : 1000000;
        this.dietType = dietType.isPresent() ? dietType.get() : Diet.BALANCED.toString();
        refreshRecipes(recipeJsons);
    }

    public ArrayList<Recipe> filter(ArrayList<Recipe> toFilter){
        ArrayList<Recipe> temp = new ArrayList<>();
        for(Recipe recipe : toFilter){
            //filter each individual variable
            /*
            eg. if(recipe.getCalories() > minCals && recipe.getCalories < maxCals) temp.add(recipe);
            but do this for each attribute and idk how i would do diettype
             */
            if(recipe.getCalories() > minCals &&
                    recipe.getCalories() < maxCals &&
                    recipe.getCooktime() > minTime &&
                    recipe.getCooktime() < maxTime &&
                    recipe.getDietLabels().equals(dietType)
            ) temp.add(recipe);
        }
        return temp;
    }

    public void refreshRecipes(List<Object> recipeJsons){
        for(Object obj : recipeJsons){
            recipeList.add(new Recipe(obj));
        }
        recipeList = filter(recipeList);
    }

    public ArrayList<Recipe> getRecipeList(){
        return recipeList;
    }

}
