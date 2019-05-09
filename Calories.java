/*
 * Keeps track of user's current and previous caloric intakes.
 * 
 * @authors Bre Chung, Dewayne Edwards, Jacob Heddings, Renan Jorge, Joon Park
 */

package nutrition_tracker;

import java.util.ArrayList;


public class Calories {

    private int calorieIntake; //allotted calorie intake
    private int dailyCalorieCount; //total calories for today
    private ArrayList<String> dailyFoodCount; // foods eaten today
    private ArrayList<Integer> foodCalorieValue; //calorie values of foods eaten today
    private ArrayList<String> calorieDates; //dates of logged calories
    private ArrayList<Integer> calorieHistory; //total calories for each date
    
    //constructor; new user's manually entered intake
    public Calories(int calorieIntake){
      this.calorieIntake = calorieIntake; 
      dailyCalorieCount = 0;
      dailyFoodCount = new ArrayList<>();
      foodCalorieValue = new ArrayList<>();
      calorieDates = new ArrayList<>();
      calorieHistory = new ArrayList<>();
    };
    
    //constructor; new user's calculated intake
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
    
    //constructor; previously logged calories for today.
    public Calories (int calorieIntake, int dailyCalorieCount, 
    		ArrayList<String> dailyFoodCount, ArrayList<Integer> foodCalorieValue) {
    	this.calorieIntake = calorieIntake;
    	this.dailyCalorieCount = dailyCalorieCount;
    	this.dailyFoodCount = dailyFoodCount;
    	this.foodCalorieValue = foodCalorieValue;

        calorieDates = new ArrayList<>();
        calorieHistory = new ArrayList<>();    
    	
    }
    
    //constructor; previously logged calories for today and previous days.
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
    
    //setters and getters
    
    public int getDailyCalorieCount(){
        return this.dailyCalorieCount;
    }
    
    public int getCalorieIntake() {
    	return this.calorieIntake;
    }
    
    public ArrayList<Integer> getCalorieHistory() {
    	return this.calorieHistory;
    }
    
    public ArrayList<String> getCalorieDates() {
    	return this.calorieDates;
    }
    
    public ArrayList<String> getDailyFoodCount() {
    	return this.dailyFoodCount;
    }    
    
    public ArrayList<Integer> getFoodCalorieValue() {
    	return this.foodCalorieValue;
    }

    
    public void setDailyCalorieCount(int dailyCalorieCount) {
    	this.dailyCalorieCount = dailyCalorieCount;
    }
    
    public void setCalorieIntake(int calorieIntake) {
    	this.calorieIntake = calorieIntake;
    }
    
    public void setCalorieHistory(ArrayList<Integer> calorieHistory) {
    	this.calorieHistory = calorieHistory;
    }
    
    public void setCalorieDates(ArrayList<String> calorieDates) {
    	this.calorieDates = calorieDates;
    }
    
    //add food to today's count
    public void addFood(String foodName, int calorieCount){
        dailyFoodCount.add(foodName);
        foodCalorieValue.add(calorieCount);
        dailyCalorieCount += calorieCount;
    }
    
    
}
