/*
 * Water object; keeps track of user's intake for today and previous days.
 * @authors Bre Chung, Dewayne Edwards, Jacob Heddings, Renan Jorge, Joon Park
 */
package nutrition_tracker;

import java.util.ArrayList;

public class Water {

    private double waterIntake; //today's water intake
    final static double dailyOz = 64.0; //64 oz is the ideal amount of water per day
    private ArrayList<String> waterDates; //dates of previous water intake
    private ArrayList<Float> waterHistory; //oz of previous water intake
    
    //constructor for new user's water object.
    public Water(){
        this.waterIntake = 0;
    }
    
    //constructor for user's water object with intake input.
    public Water(double currentIntake){
        this.waterIntake = currentIntake;
    }
    
    //constructor for existing user; loads current input and previous water logs.
    public Water(double currentIntake, ArrayList<String> waterDates, ArrayList<Float> waterHistory) {
    	waterIntake = currentIntake;
    	this.waterDates = waterDates;
    	this.waterHistory = waterHistory;
    }
    
    //add water to current intake.
    public void addWater(double currentIntake){
        waterIntake += currentIntake;
    }
    
    //setters and getters
    
    public double getWater(){
        return this.waterIntake;
    }
    
    public ArrayList<String> getWaterDates() {
    	return this.waterDates;
    }
    
    public ArrayList<Float> getWaterHistory() {
    	return this.waterHistory;
    }
    
    public void setWaterDates(ArrayList<String> waterDates) {
    	this.waterDates = waterDates;
    }

    public void setWaterHistory(ArrayList<Float> waterHistory) {
    	this.waterHistory = waterHistory;
    }    
    
}
