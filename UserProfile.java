package nutrition_tracker;

import java.util.ArrayList;

public class UserProfile {
	private String username;
	private int age; 
	private int heightInInches;
	private float weightInPounds;
	private char gender;
	private Calories calInfo;
	private Weight weightInfo;
	Water waterInfo;
	
	public UserProfile(String username, int age, int heightInInches, float weightInPounds,
					  char gender) {
		this.username = username;
		this.age = age;
		this.heightInInches = heightInInches;
		this.weightInPounds = weightInPounds;
		this.gender = gender;
		
		calInfo = new Calories(age, heightInInches, weightInPounds, gender);
		weightInfo = new Weight(weightInPounds);
		waterInfo = new Water();
	}
	
	public UserProfile(String username, int age, int heightInInches, float weightInPounds,
			  char gender, int calorieIntake) {
		this.username = username;
		this.age = age;
		this.heightInInches = heightInInches;
		this.weightInPounds = weightInPounds;
		this.gender = gender;
		
		calInfo = new Calories(calorieIntake);
		weightInfo = new Weight(weightInPounds);
		waterInfo = new Water();
	}
	
	public UserProfile(String username, int age, int heightInInches, 
			float weightInPounds, char gender, 
			int calorieIntake, int dailyCalorieCount,
			ArrayList<String> dailyFoodCount,
			ArrayList<Integer> foodCalorieValue,
			ArrayList<String> calorieDates, 
			ArrayList<Integer> calorieHistory, 
			ArrayList<String> weightDates, 
			ArrayList<Float> weightHistory,
			int currentWaterIntake,
			ArrayList<String> waterDates, 
			ArrayList<Integer> waterHistory) {
		this.username = username;
		this.age = age;
		this.heightInInches = heightInInches;
		this.weightInPounds = weightInPounds;
		this.gender = gender;
		
		calInfo = new Calories(calorieIntake, dailyCalorieCount, dailyFoodCount, foodCalorieValue, 
					calorieDates, calorieHistory);
		weightInfo = new Weight (weightInPounds, weightDates, weightHistory);
		waterInfo = new Water(currentWaterIntake, waterDates, waterHistory);
	}
	
	public Calories getCalInfo() {
		return calInfo;
	}
	
	public Water getWaterInfo() {
		return waterInfo;
	}
	
	public String getUsername() {
		return username;
	}
	
	public int getAge() {
		return age;
	}
	

}
