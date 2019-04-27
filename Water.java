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
public class Water {

    private double waterIntake;
    final static double dailyOz = 64.0;
    private ArrayList<String> waterDates;
    private ArrayList<Integer> waterHistory;
    
    public Water(){
        this.waterIntake = 0;
    }
    public Water(double currentIntake){
        this.waterIntake = currentIntake;
    }
    public Water(double currentIntake, ArrayList<String> waterDates, ArrayList<Integer> waterHistory) {
    	waterIntake = currentIntake;
    	this.waterDates = waterDates;
    	this.waterHistory = waterHistory;
    }
    
    public void addWater(double currentIntake){
        waterIntake += currentIntake;
    }
    
    public double getWater(){
        return this.waterIntake;
    }
}
