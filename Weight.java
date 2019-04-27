package nutrition_tracker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Weight {
	private float currentWeight;
	private ArrayList<String> weightDates;
	private ArrayList<Float> weightHistory;
	
	public Weight (float currentWeight) {
		this.currentWeight = currentWeight;
		weightDates = new ArrayList<String>();
		weightHistory = new ArrayList<Float>();
	}
	
	public Weight (float currentWeight, ArrayList<String> weightDates, 
				 ArrayList<Float> weightHistory) {
		this.currentWeight = currentWeight;
		this.weightDates = weightDates;
		this.weightHistory = weightHistory;
	}	
	
	public float getCurrentWeight() {
		return currentWeight;
	}
	
	public ArrayList<String> getWeightDates() {
		return weightDates;
	}
	
	public ArrayList<Float> getWeightHistory() {
		return weightHistory;
	}	
	
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
