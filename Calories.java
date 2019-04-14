/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nutrition_tracker;

import java.util.ArrayList;

/**
 *
 * @author Andrew
 */
public class Calories {

    private int calorieIntake;
    private int dailyCalorieCount;
    private ArrayList<String> dailyFoodCount;
    private ArrayList<Integer> foodCalorieValue;
    private ArrayList<String> calorieDates;
    private ArrayList<Integer> calorieHistory;
    
    public Calories(int calorieIntake){
      calorieIntake = this.calorieIntake; 
      dailyCalorieCount = 0;
      dailyFoodCount = new ArrayList<>();
      foodCalorieValue = new ArrayList<>();
      calorieDates = new ArrayList<>();
      calorieHistory = new ArrayList<>();
    };

    public int getDailyCalorieCount(){
        return this.dailyCalorieCount;
    }
    
    public void addFood(String foodName, int calorieCount){
        dailyFoodCount.add(foodName);
        foodCalorieValue.add(calorieCount);
        dailyCalorieCount += calorieCount;
    }
    
    
}
