/*
 * A user's profile for the nutrition tracker; keeps profile
 * information as well as their calorie, weight, and water information.
 * 
 * @authors Bre Chung, Dewayne Edwards, Jacob Heddings, Renan Jorge, Joon Park
 */

package nutrition_tracker;

public class UserProfile {
	private String username; //user's username
	private int age; //user's age
	private int heightInInches; //user's height
	private float weightInPounds; //user's weight
	private char gender; //user's gender
	private Calories calInfo; //user's calorie information
	private Weight weightInfo; //user's weight
	Water waterInfo; //user's water information
	
	//constructor for new profile; automatic calculation of caloric intake
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
	
	//constructor for new profile; manual calculation of caloric intake
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
	
	//constructor for returning user
	public UserProfile(String username, int age, int heightInInches, 
			float weightInPounds, char gender, 
			Calories calInfo,
			Weight weightInfo,
			Water waterInfo) {
		this.username = username;
		this.age = age;
		this.heightInInches = heightInInches;
		this.weightInPounds = weightInPounds;
		this.gender = gender;
		
		this.calInfo = calInfo;
		this.weightInfo = weightInfo;
		this.waterInfo = waterInfo;
	}
	
	//various setters and getters
	
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
	
	public int getHeightInInches() {
		return heightInInches;
	}
	
	public float getWeightInPounds() {
		return weightInPounds;
	}
	
	public char getGender() {
		return gender;
	}
	
	public Weight getWeightInfo() {
		return weightInfo;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public void setHeightInInches(int heightInInches) {
		this.heightInInches = heightInInches;
	}
	
	public void setWeightInPounds(float weightInPounds) {
		this.weightInPounds = weightInPounds;
	}
	
	

}
