/*
 * Keeps track of user's current weight and history.
 * 
 * @authors Bre Chung, Dewayne Edwards, Jacob Heddings, Renan Jorge, Joon Park
 */

package nutrition_tracker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Weight {
	private float currentWeight; //user's current weight
	private ArrayList<String> weightDates; //dates user previously logged weight.
	private ArrayList<Float> weightHistory; //previously logged weights
	
	//constructor; new user.
	public Weight (float currentWeight) {
		this.currentWeight = currentWeight;
		weightDates = new ArrayList<String>(); 
		weightHistory = new ArrayList<Float>();
	}
	
	//constructor; loaded previously logged weights.
	public Weight (float currentWeight, ArrayList<String> weightDates, 
				 ArrayList<Float> weightHistory) {
		this.currentWeight = currentWeight;
		this.weightDates = weightDates;
		this.weightHistory = weightHistory;
	}	
	
	//setters and getters
	public float getCurrentWeight() {
		return currentWeight;
	}
	
	public ArrayList<String> getWeightDates() {
		return weightDates;
	}
	
	public ArrayList<Float> getWeightHistory() {
		return weightHistory;
	}	
	
	//add a new weight to previous weight history.
	public void addNewWeight (float newWeight) {
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date d = new Date();
		weightHistory.add(currentWeight);
		weightDates.add(df.format(d));
		
		currentWeight = newWeight;
	}
	
	public void removeWeight (String date) {
		//to be finished
	}
	

}
