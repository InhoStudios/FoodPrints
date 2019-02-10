package com.inhostudios.visionapitester.DataExtractionModel;

import java.util.ArrayList;
import java.util.List;

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
    public RecipeManager(List<Object> recipeJsons){
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
