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
      this.calorieIntake = calorieIntake; 
      dailyCalorieCount = 0;
      dailyFoodCount = new ArrayList<>();
      foodCalorieValue = new ArrayList<>();
      calorieDates = new ArrayList<>();
      calorieHistory = new ArrayList<>();
    };
    
    public Calories(int age, int heightInInches, float weightInPounds, char gender) {
    	
    	//Mifflin-St Jeor Equation
    	if (new Character(Character.toLowerCase(gender)).equals('m'))
    		calorieIntake = (int) Math.round((weightInPounds * 0.453592 * 10) + 
    					(2.54 * 6.25 * heightInInches) + (5 * age) + 5);
    	else 
    		calorieIntake = (int) Math.round((weightInPounds * 0.453592 * 10) + 
					(2.54 * 6.25 * heightInInches) - (5 * age) - 161);  
    	
        dailyCalorieCount = 0;
        dailyFoodCount = new ArrayList<>();
        foodCalorieValue = new ArrayList<>();
        calorieDates = new ArrayList<>();
        calorieHistory = new ArrayList<>();    	
    		
    }
    
    public Calories (int calorieIntake, int dailyCalorieCount, 
    		ArrayList<String> dailyFoodCount, ArrayList<Integer> foodCalorieValue) {
    	this.calorieIntake = calorieIntake;
    	this.dailyCalorieCount = dailyCalorieCount;
    	this.dailyFoodCount = dailyFoodCount;
    	this.foodCalorieValue = foodCalorieValue;

        calorieDates = new ArrayList<>();
        calorieHistory = new ArrayList<>();    
    	
    }
    
    public Calories (int calorieIntake, int dailyCalorieCount, 
    		ArrayList<String> dailyFoodCount, ArrayList<Integer> foodCalorieValue, 
    		ArrayList<String> calorieDates, ArrayList<Integer> calorieHistory) {
    	this.calorieIntake = calorieIntake;
    	this.dailyCalorieCount = dailyCalorieCount;
    	this.dailyFoodCount = dailyFoodCount;
    	this.foodCalorieValue = foodCalorieValue;
        this.calorieDates = calorieDates;
        this.calorieHistory = calorieHistory;    	
    	
    }

    public int getDailyCalorieCount(){
        return this.dailyCalorieCount;
    }
    
    public int getCalorieIntake() {
    	return this.calorieIntake;
    }
    
    public void addFood(String foodName, int calorieCount){
        dailyFoodCount.add(foodName);
        foodCalorieValue.add(calorieCount);
        dailyCalorieCount += calorieCount;
    }
    
    
}
