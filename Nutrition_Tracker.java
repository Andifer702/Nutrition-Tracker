/*
 * A GUI program that allows users to keep profiles
 * That track their calories, water consumption, and weight.
 * 
 * @authors Bre Chung, Dewayne Edwards, Jacob Heddings, Renan Jorge, Joon Park
 */
package nutrition_tracker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.text.*;
import org.jfree.chart.ChartPanel;


public class Nutrition_Tracker {

	
	// Variable Declarations
	private JFrame mainFrame;
	private JTabbedPane tabPane;
	private JPanel summaryPanel;
	private JPanel addPanel;
	private JPanel graphPanel;

	private JTextPane messageText;
	private JPanel inputFoodPanel;
	private JScrollPane foodSummaryPane;
	private JTextArea foodSummaryText;

	private JLabel food;
	private JLabel cal;
	private JTextField foodTextfield;
	private JTextField caloriesTextfield;
	private JButton inputFoodButton;
	private JButton clearButton;
	private JButton search;
	private JLabel water;
	private JTextField waterAmount;
	private JButton inputWaterButton;
	private JLabel waterUnit;
	private JLabel blank;

	private JFrame frame;
	private JTextField userField;
	private JNumberTextField ageField;
	private JFloatTextField weightField;
	private JNumberTextField HeightFtField;
	private JNumberTextField HeightInField;
	private JNumberTextField calField;

	private UserProfile currentUser;
	private FoodDatabase foodDatabase;

	private Date date;
	private Calories userCalorie;
	private Water userWater;

	private GraphUserInfo userGraph;
	private ChartPanel chartPanel;

	private BufferedWriter eatWriter;
	private BufferedWriter profileWriter;

	private static String profileSuffix = "_profile.txt";
	private static String eatSuffix = "_eatHistory.txt";
	private static String mainDirectory = System.getProperty("user.dir");

	private static String countKeyword = "count";
	private static String foodKeyword = "food";
	private static String waterKeyword = "water";
	private String formattedDate;

	// Constructor
	public Nutrition_Tracker() {
		buildLoginGUI();
	}

	//builds the GUI that will allow users to login or register
	private void buildLoginGUI() {

		frame = new JFrame();
		frame.setBounds(100, 100, 300, 380);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {

				System.exit(0);
			}
		});

		JTabbedPane loginPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(loginPane, BorderLayout.CENTER);
		
		//Login tab
		JPanel loginTab = new JPanel();
		loginPane.addTab("Login", null, loginTab, null);
		loginTab.setLayout(null);

		JButton loginButton = new JButton("Login");
		loginButton.setBounds(79, 279, 117, 29);
		loginTab.add(loginButton);

		JTextArea welcomeText = new JTextArea();
		welcomeText.setBackground(UIManager.getColor("Button.background"));
		welcomeText.setEditable(false);
		welcomeText.setWrapStyleWord(true);
		welcomeText.setLineWrap(true);
		welcomeText.setText("Welcome. Please selected your profile or register using the \"Register\" tab.");
		welcomeText.setBounds(20, 6, 237, 42);
		loginTab.add(welcomeText);

		String profileName = "";

		ArrayList<String> profiles = new ArrayList<String>();
		File currFolder = new File(mainDirectory);
		File[] allFiles = currFolder.listFiles();
		for (File f : allFiles) {
			if (f.getName().contains(profileSuffix)) {
				profileName = f.getName().split("_profile")[0];
				profiles.add(profileName);
			}
		}
		
		//lists available profiles
		JList list = new JList(profiles.toArray());
		if (!profiles.isEmpty()) {
			list.setSelectedIndex(0);
		} else {
			loginButton.setEnabled(false);
		}
		list.setBounds(6, 56, 267, 211);
		loginTab.add(list);
		
		//login button; parse user's files and load main GUI
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginButton.setEnabled(false);
				File newFile = new File(mainDirectory + "/" + (String) list.getSelectedValue() + eatSuffix);
				if (newFile.exists()) {
					currentUser = parseAll((String) list.getSelectedValue());
					buildGUI();
					frame.setVisible(false);
					// currentUser = parseAll((String) list.getSelectedValue());

				} else {
					currentUser = parseHistoryOnly((String) list.getSelectedValue());
					buildGUI();
					frame.setVisible(false);
				}
			}
		});
		
		//Registration tab
		JPanel registerTab = new JPanel();
		loginPane.addTab("Register", null, registerTab, null);
		registerTab.setLayout(null);

		userField = new JTextField();
		userField.setBounds(143, 16, 130, 26);
		registerTab.add(userField);
		userField.setColumns(10);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(6, 21, 98, 16);
		registerTab.add(lblUsername);

		JLabel lblAge = new JLabel("Age:");
		lblAge.setBounds(6, 55, 98, 16);
		registerTab.add(lblAge);

		ageField = new JNumberTextField();
		ageField.setColumns(10);
		ageField.setBounds(143, 50, 130, 26);
		registerTab.add(ageField);

		weightField = new JFloatTextField();
		weightField.setColumns(10);
		weightField.setBounds(184, 83, 50, 26);
		registerTab.add(weightField);

		JLabel lblWeightinPounds = new JLabel("Weight (decimals allowed):");
		lblWeightinPounds.setBounds(6, 88, 176, 16);
		registerTab.add(lblWeightinPounds);

		JLabel lblHeight = new JLabel("Height:");
		lblHeight.setBounds(6, 121, 130, 16);
		registerTab.add(lblHeight);

		HeightFtField = new JNumberTextField();
		HeightFtField.setColumns(10);
		HeightFtField.setBounds(143, 116, 39, 26);
		registerTab.add(HeightFtField);

		JLabel lblFt = new JLabel("ft.");
		lblFt.setBounds(187, 121, 19, 16);
		registerTab.add(lblFt);

		JLabel lblIn = new JLabel("in.");
		lblIn.setBounds(260, 121, 19, 16);
		registerTab.add(lblIn);

		HeightInField = new JNumberTextField();
		HeightInField.setColumns(10);
		HeightInField.setBounds(218, 116, 39, 26);
		registerTab.add(HeightInField);

		String[] genderList = { "Male", "Female" };
		JComboBox<String> genderBox = new JComboBox<String>(genderList);
		genderBox.setBounds(143, 154, 130, 27);
		registerTab.add(genderBox);

		JLabel lblGender = new JLabel("Gender:");
		lblGender.setBounds(6, 158, 130, 16);
		registerTab.add(lblGender);

		JButton registerSubmit = new JButton("Register");
		registerSubmit.setBounds(78, 258, 117, 29);
		registerTab.add(registerSubmit);

		JRadioButton rdbtnEnterCalorieIntake = new JRadioButton("Enter Calorie Intake");
		rdbtnEnterCalorieIntake.setBounds(92, 193, 181, 23);
		registerTab.add(rdbtnEnterCalorieIntake);
		rdbtnEnterCalorieIntake.setSelected(true);

		JRadioButton rdbtnCalculateCalorieIntake = new JRadioButton("Calculate Calorie Intake");
		rdbtnCalculateCalorieIntake.setBounds(92, 214, 181, 23);
		registerTab.add(rdbtnCalculateCalorieIntake);

		ButtonGroup calButtons = new ButtonGroup();
		calButtons.add(rdbtnEnterCalorieIntake);
		calButtons.add(rdbtnCalculateCalorieIntake);
		
		//disables calorie box if the intake will be calculated
		rdbtnCalculateCalorieIntake.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// calField.setEditable(false);
				calField.setEnabled(false);
			}
		});
		
		//enables calorie box if the intake will be manually added
		rdbtnEnterCalorieIntake.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calField.setEnabled(true);
			}
		});

		JLabel lblLbs = new JLabel("lbs.");
		lblLbs.setBounds(238, 88, 31, 16);
		registerTab.add(lblLbs);

		calField = new JNumberTextField();
		calField.setColumns(10);
		calField.setBounds(6, 192, 50, 26);
		registerTab.add(calField);

		JLabel lblCals = new JLabel("cals.");
		lblCals.setBounds(59, 197, 31, 16);
		registerTab.add(lblCals);

		//Registration submit button; creates new user object and files
		registerSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean allFilled = true;
				ArrayList<String> unfilledFields = new ArrayList<String>();
				String userInput = userField.getText().trim();
				if (userInput.equals("")) {
					allFilled = false;
					unfilledFields.add("Username");
				}
				int ageInput = Math.toIntExact(ageField.getNumber());
				if (ageInput <= 0) {
					allFilled = false;
					unfilledFields.add("Age");
				}
				float weightInput = weightField.getNumber();
				if (weightInput <= 0) {
					allFilled = false;
					unfilledFields.add("Weight");
				}
				int inchInput = Math.toIntExact(HeightInField.getNumber());
				if (inchInput < 0) {
					allFilled = false;
					unfilledFields.add("Height (in.)");
				}
				int footInput = Math.toIntExact(HeightFtField.getNumber());
				if (footInput <= 0) {
					allFilled = false;
					unfilledFields.add("Height (ft.)");
				}
				int calInput = 0;
				if (calField.isEnabled()) {
					calInput = Math.toIntExact(calField.getNumber());
					if (calInput <= 0) {
						allFilled = false;
						unfilledFields.add("Calories");
					}
				}

				if (!allFilled) {
					String alertMessage = "The following fields are not filled out: ";
					for (int i = 0; i < unfilledFields.size() - 1; i++) {
						alertMessage += unfilledFields.get(i) + ", ";
					}
					alertMessage += unfilledFields.get(unfilledFields.size() - 1) + ".";
					JOptionPane.showMessageDialog(frame, alertMessage);
				} else {

					int totalHeight = (footInput * 12) + inchInput;
					char genChar;
					if (genderBox.getSelectedItem().equals(genderList[0])) {
						genChar = 'M';
					} else {
						genChar = 'F';
					}

					if (rdbtnCalculateCalorieIntake.isSelected()) {
						currentUser = new UserProfile(userInput, ageInput, totalHeight, weightInput, genChar);
					} else if (rdbtnEnterCalorieIntake.isSelected()) {
						currentUser = new UserProfile(userInput, ageInput, totalHeight, weightInput, genChar, calInput);
					}
					createFile();
					buildGUI();
					frame.setVisible(false);
				}
			}
		});

		frame.setVisible(true);
	}

	//Main program GUI.
	private void buildGUI() {
		mainFrame = new JFrame("Nutrition Tracker");
		mainFrame.setSize(450, 600);

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {

				writeCount();
				System.exit(0);
			}
		});

		// hardcoding user information
		// default calorie built for testing
		tabPane = new JTabbedPane();

		// Build Main Page GUI
		summaryPanel = new JPanel(new BorderLayout());
		messageText = new JTextPane();
		messageText.setEditable(false);
		StyledDocument doc = messageText.getStyledDocument();

		Date date = new Date();
		formattedDate = String.format("%tD", date);

		// defining text styles
		final SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength() - 1, center, false);

		Style headingStyle = doc.addStyle("Heading", null);
		headingStyle.addAttribute(StyleConstants.FontSize, 16);
		headingStyle.addAttribute(StyleConstants.FontFamily, "Verdana");
		headingStyle.addAttribute(StyleConstants.Bold, new Boolean(true));

		Style paraStyle = doc.addStyle("paragraph", null);
		paraStyle.addAttribute(StyleConstants.FontSize, 12);
		paraStyle.addAttribute(StyleConstants.FontFamily, "Verdana");

		// inserting messages
		try {
			doc.insertString(doc.getLength(), "\n\nWelcome " + currentUser.getUsername() + "!" + "\n", headingStyle);
			doc.insertString(doc.getLength(), "Today is: " + formattedDate + "\n\n", headingStyle);

			doc.insertString(doc.getLength(), "You have consumed " + currentUser.getCalInfo().getDailyCalorieCount()
					+ " out of " + currentUser.getCalInfo().getCalorieIntake() + " calories\n", paraStyle);
			doc.insertString(doc.getLength(),
					"And drank " + roundPrecision(currentUser.getWaterInfo().getWater(), 1) + " oz of water",
					paraStyle);

		} catch (BadLocationException e) {
			e.printStackTrace();
		}

		// Add Main GUI panel to the 1st tab
		summaryPanel.add(messageText, BorderLayout.CENTER);
		tabPane.addTab("Main", summaryPanel);

		// Build AddFood GUI
		addPanel = new JPanel(new BorderLayout(0, 30));
		inputFoodPanel = new JPanel();
		inputFoodPanel.setLayout(new WrapLayout());

		food = new JLabel("Food: ");
		cal = new JLabel("Calories: ");
		foodTextfield = new JTextField();
		foodTextfield.setPreferredSize(new Dimension(100, 25));
		caloriesTextfield = new JTextField();
		caloriesTextfield.setPreferredSize(new Dimension(60, 25));
		inputFoodButton = new JButton("Submit");
		search = new JButton("Search");

		water = new JLabel("Water: ");
		waterAmount = new JTextField();
		waterAmount.setPreferredSize(new Dimension(40, 25));
		waterUnit = new JLabel("oz ");
		inputWaterButton = new JButton("Submit");

		blank = new JLabel("           ");
		clearButton = new JButton("Clear");

		// add components to the top panel
		inputFoodPanel.add(food);
		inputFoodPanel.add(foodTextfield);
		inputFoodPanel.add(cal);
		inputFoodPanel.add(caloriesTextfield);
		inputFoodPanel.add(search);
		inputFoodPanel.add(inputFoodButton);
		inputFoodPanel.add(water);
		inputFoodPanel.add(waterAmount);
		inputFoodPanel.add(waterUnit);
		inputFoodPanel.add(blank);
		inputFoodPanel.add(inputWaterButton);
		inputFoodPanel.add(clearButton);

		// add scrollable textpane in the center
		foodSummaryText = new JTextArea();
		foodSummaryText.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		foodSummaryText.setEditable(false);

		foodSummaryPane = new JScrollPane(foodSummaryText);
		foodSummaryPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		foodSummaryPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		foodSummaryText.append("Food consumption summary message will go here\n");

		addPanel.add(inputFoodPanel, BorderLayout.NORTH);
		addPanel.add(foodSummaryPane, BorderLayout.CENTER);

		// add AddFood GUI to the 2nd tab
		tabPane.addTab("Add Nutrition", addPanel);

		// Build Graph GUI
		graphPanel = new JPanel(new BorderLayout());
		userGraph = new GraphUserInfo(currentUser);

		chartPanel = new ChartPanel(userGraph.calorieGraph);

		graphPanel.add(chartPanel, BorderLayout.CENTER);

		// add Graph GUI to the 3rd tab
		tabPane.addTab("Graph", graphPanel);

		// add tabPane to mainFrame
		mainFrame.add(tabPane);
		mainFrame.setVisible(true);

		// Button action for inputting food data
		inputFoodButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// input food name and calorie information
				String foodInput = foodTextfield.getText().toLowerCase();

				// check to see if the calorie input is an integer
				if (isInteger(caloriesTextfield.getText())) {
					int calorieInput = Integer.parseInt(caloriesTextfield.getText());

					// update userCalorie and updated food summary text panel
					currentUser.getCalInfo().addFood(foodInput, calorieInput);
					foodSummaryText.append("You ate " + foodInput + ". Caloric value: " + calorieInput + "\n");

					// formats the string then write to file
					foodInput = foodInput.replaceAll(" ", "_");
					writeToEatFile(
							foodKeyword + " " + formattedDate + " " + foodInput + " " + caloriesTextfield.getText());
				} else {
					JOptionPane.showMessageDialog(null, "Please input integer for calories");
				}

				foodTextfield.setText("");
				caloriesTextfield.setText("");

				// update Summary Panel
				messageText.setText("");
				try {
					doc.insertString(doc.getLength(), "\n\nWelcome " + currentUser.getUsername() + "\n", headingStyle);
					doc.insertString(doc.getLength(), "Today is: " + formattedDate + "\n\n", headingStyle);

					doc.insertString(
							doc.getLength(), "You have consumed " + currentUser.getCalInfo().getDailyCalorieCount()
									+ " out of " + currentUser.getCalInfo().getCalorieIntake() + " calories\n",
							paraStyle);
					doc.insertString(doc.getLength(),
							"And drank " + roundPrecision(currentUser.getWaterInfo().getWater(), 1) + " oz of water",
							paraStyle);

				} catch (BadLocationException ex) {
					ex.printStackTrace();
				}

			}
		});

		// button action for inputting water intake
		inputWaterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// input amount of water consumed
				if (isDouble(waterAmount.getText())) {
					double waterInput = Double.parseDouble(waterAmount.getText());
					currentUser.waterInfo.addWater(waterInput);
					foodSummaryText.append("You drank " + waterInput + " oz of water.\n");
					writeToEatFile(waterKeyword + " " + formattedDate + " " + waterAmount.getText());
				} else {
					JOptionPane.showMessageDialog(null, "Please use integer/double for water drank");
				}

				waterAmount.setText("");

				// Update Summary Panel
				messageText.setText("");
				try {
					doc.insertString(doc.getLength(), "\n\nWelcome " + currentUser.getUsername() + "\n", headingStyle);
					doc.insertString(doc.getLength(), "Today is: " + formattedDate + "\n\n", headingStyle);

					doc.insertString(
							doc.getLength(), "You have consumed " + currentUser.getCalInfo().getDailyCalorieCount()
									+ " out of " + currentUser.getCalInfo().getCalorieIntake() + " calories\n",
							paraStyle);
					doc.insertString(doc.getLength(),
							"And drank " + roundPrecision(currentUser.getWaterInfo().getWater(), 1) + " oz of water",
							paraStyle);

				} catch (BadLocationException ex) {
					ex.printStackTrace();
				}
			}
		});

		// button action for search
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// perform search for food
				try {
					// retrieve user food search input and instantiate class
					// with the user supplied parameter
					String foodSearch = foodTextfield.getText();
					foodDatabase = new FoodDatabase(foodSearch);
					// retrieve the result of the search and assigb value to
					// stringbuilder sb
					StringBuilder sb = new StringBuilder();
					sb = foodDatabase.getFoodResult();

					// check if food item is found on the database and value is
					// returned
					if (sb.length() != 0) {
						// Create JPanel and display the result of the food
						// search
						Object[] options = { "Add Food", "Cancel" };
						String[] sbString = sb.toString().split("\\r?\\n");
						JLabel title = new JLabel("Calories per 100g.");
						ButtonGroup bg = new ButtonGroup();
						JPanel foodSearchPanel = new JPanel();
						BoxLayout boxLayout = new BoxLayout(foodSearchPanel, BoxLayout.Y_AXIS);
						foodSearchPanel.setLayout(boxLayout);
						foodSearchPanel.setBorder(new EmptyBorder(new Insets(30, 50, 30, 50)));
						foodSearchPanel.add(title);

						// iterate through the search result and create a radio
						// button for each item
						for (int i = 0; i < sbString.length; i++) {
							JRadioButton rdButton = new JRadioButton();
							rdButton.setText(sbString[i]);
							foodSearchPanel.add(Box.createRigidArea(new Dimension(0, 10)));
							bg.add(rdButton);
							foodSearchPanel.add(rdButton);
						}

						// display JPanel and retrieve the user selection
						int result = JOptionPane.showOptionDialog(null, foodSearchPanel, "Food Search",
								JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
						if (result == JOptionPane.OK_OPTION) {
							for (Enumeration<AbstractButton> buttons = bg.getElements(); buttons.hasMoreElements();) {
								AbstractButton button = buttons.nextElement();
								if (button.isSelected()) {

									// split the string from the selected radio
									// button and assign values to foodtext
									// field and caloriestextfield
									String[] splitFood = (button.getText()).split(" ");
									foodTextfield.setText(splitFood[0]);
									caloriesTextfield.setText(splitFood[1]);
								}
							}
						}
						// if food is not found on the database, display message
						// warning the user and clear the foodtextfield
					} else {
						JOptionPane.showMessageDialog(null, "There are no " + foodSearch + " in the food database.");
						foodTextfield.setText("");
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});

		// button action for clearing input data
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				foodTextfield.setText("");
				caloriesTextfield.setText("");
				waterAmount.setText("");
			}
		});

	}

	// checking user input for calories
	public boolean isInteger(String string) {
		try {
			Integer.valueOf(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	// checking user input for water drank
	public boolean isDouble(String string) {
		try {
			Double.valueOf(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	//Creates a user's profile files; one for main profile information and one for eating history.
	public void createFile() {
		File profileFile = new File(currentUser.getUsername() + "_profile.txt");
		File eatFile = new File(currentUser.getUsername() + "_eatHistory.txt");

		try {
			if (!profileFile.exists()) {
				profileFile.createNewFile();
				eatFile.createNewFile();
			} else {
				System.out.println("File already exists");
			}

			eatWriter = new BufferedWriter(new FileWriter(eatFile, true));
			profileWriter = new BufferedWriter(new FileWriter(profileFile, true));
			writeToProfileFile(currentUser.getAge() + "");
			writeToProfileFile(currentUser.getWeightInPounds() + "");
			int heightRem = currentUser.getHeightInInches() % 12;
			writeToProfileFile((currentUser.getHeightInInches() - heightRem) / 12 + " " + heightRem + "");
			writeToProfileFile(currentUser.getGender() + "");
			writeToProfileFile(currentUser.getCalInfo().getCalorieIntake() + "");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error creating file");
		}

	}
	
	//Write line to eating history file
	public void writeToEatFile(String str) {
		try {
			eatWriter.write(str);
			eatWriter.newLine();
			eatWriter.flush();
		} catch (IOException e) {
			e.toString();
		}

	}
	
	//Write line to profile file
	public void writeToProfileFile(String str) {
		try {
			profileWriter.write(str);
			profileWriter.newLine();
			profileWriter.flush();
		} catch (IOException e) {
			e.toString();
		}

	}
	
	//Parse the user's information in their profiles to create a user profile
	public UserProfile parseAll(String username) {

		File eatFile = new File(mainDirectory + "/" + username + eatSuffix);
		File profileFile = new File(mainDirectory + "/" + username + profileSuffix);

		int profileAge = 0;
		float profileWeight = 0;
		int profileFeet = 0;
		int profileInches = 0;
		char profileGender = 'm';
		int profileIntake = 0;
		Calories calInfo = null;
		Water waterInfo = null;
		Weight weightInfo = null;
		Weight profileWeights;
		try {
			Scanner historyScan = new Scanner(profileFile);
			profileWriter = new BufferedWriter(new FileWriter(profileFile, true));
			profileAge = Integer.valueOf(historyScan.nextLine().trim()).intValue();
			profileWeight = Float.valueOf(historyScan.nextLine().trim()).floatValue();
			String[] height = historyScan.nextLine().split("\\s+");
			profileFeet = Integer.valueOf(height[0].trim()).intValue();
			profileInches = Integer.valueOf(height[1].trim()).intValue();
			profileGender = historyScan.nextLine().trim().charAt(0);
			profileIntake = Integer.valueOf(historyScan.nextLine().trim()).intValue();

			ArrayList<String> weightDates = new ArrayList<String>();
			ArrayList<Float> weightHistory = new ArrayList<Float>();
			while (historyScan.hasNextLine()) {
				String weightLine = historyScan.nextLine();
				if (weightLine.contains("weight")) {
					String[] weightSplit = weightLine.split("\\s+");
					weightDates.add(weightSplit[1]);
					weightHistory.add((Float.valueOf(weightSplit[2].trim()).floatValue()));
				}
				;
				profileWeights = new Weight(profileWeight, weightDates, weightHistory);

			}
			historyScan.close();
		} catch (FileNotFoundException ex) {
			ex.toString();
		} catch (IOException ex) {
			ex.toString();
		}

		try {
			Scanner foodScan = new Scanner(eatFile);

			eatWriter = new BufferedWriter(new FileWriter(eatFile, true));
			ArrayList<String> calorieDates = new ArrayList<String>();
			ArrayList<Integer> calorieHistory = new ArrayList<Integer>();
			ArrayList<String> dailyFoodCount = new ArrayList<String>();
			ArrayList<Integer> foodCalorieValue = new ArrayList<Integer>();

			double waterIntake = 0;
			ArrayList<String> waterDates = new ArrayList<String>();
			ArrayList<Float> waterHistory = new ArrayList<Float>();

			DateFormat df = new SimpleDateFormat("MM/dd/yy");
			Date date = new Date();
			String todaysDate = df.format(date);

			while (foodScan.hasNextLine()) {
				String[] lineSplit = foodScan.nextLine().split("\\s+");
				if (lineSplit.length > 1) {
					if (lineSplit[0].trim().equals(countKeyword)) {
						calorieDates.add(lineSplit[1].trim());
						calorieHistory.add(Integer.valueOf(lineSplit[2].trim()).intValue());
						waterDates.add(lineSplit[1].trim());
						waterHistory.add(Float.valueOf(lineSplit[3].trim()).floatValue());
					} else if (lineSplit[1].trim().equals(todaysDate)) {
						if (lineSplit[0].trim().equals(foodKeyword)) {
							dailyFoodCount.add(lineSplit[2].trim());
							foodCalorieValue.add(Integer.valueOf(lineSplit[3].trim()).intValue());

						} else if (lineSplit[0].trim().equals(waterKeyword)) {
							waterIntake += Float.valueOf(lineSplit[2].trim()).floatValue();
						}
					}
				}
			}

			waterInfo = new Water(waterIntake, waterDates, waterHistory);
			int totalCalories = 0;
			for (Integer d : foodCalorieValue) {
				totalCalories += d;
			}
			calInfo = new Calories(profileIntake, totalCalories, dailyFoodCount, foodCalorieValue, calorieDates,
					calorieHistory);

			foodScan.close();
		} catch (FileNotFoundException ex) {
			ex.toString();
		} catch (IOException ex) {
			ex.toString();
		}

		UserProfile tempProf = new UserProfile(username, profileAge, (profileFeet * 12) + profileInches, profileWeight,
				profileGender, calInfo, weightInfo, waterInfo);

		return tempProf;
	}
	
	//write the current count when the user closes the program

	public void writeCount() {
		try {
			File eatFile = new File(mainDirectory + "/" + currentUser.getUsername() + eatSuffix);
			BufferedReader read = new BufferedReader(new FileReader(eatFile));
			BufferedWriter write = new BufferedWriter(new FileWriter(eatFile, true));

			String currentLine = "";
			String countRemove = "count " + formattedDate;
			String fullCount = String.format("%s %d %.1f\n", countRemove,
					currentUser.getCalInfo().getDailyCalorieCount(), currentUser.getWaterInfo().getWater());
			boolean hasLine = false;
			while ((currentLine = read.readLine()) != null) {
				if (currentLine.trim().contains(fullCount.trim())) {
					hasLine = true;
					break;
				}
			}
			if (!hasLine) {
				write.write(fullCount);
			}
			read.close();
			write.close();

		} catch (FileNotFoundException ex) {
			ex.toString();
		} catch (IOException ex) {
			ex.toString();
		}
	}

	public UserProfile parseHistoryOnly(String username) {

		File historyFile = new File(mainDirectory + "/" + username + profileSuffix);
		int profileAge = 0;
		float profileWeight = 0;
		int profileFeet = 0;
		int profileInches = 0;
		char profileGender = 'm';
		int profileIntake = 0;
		Calories calInfo = null;
		Water waterInfo = null;
		Weight weightInfo = null;
		Weight profileWeights;
		try {
			Scanner historyScan = new Scanner(historyFile);

			profileAge = Integer.valueOf(historyScan.nextLine().trim()).intValue();
			profileWeight = Float.valueOf(historyScan.nextLine().trim()).floatValue();
			String[] height = historyScan.nextLine().split("\\s+");
			profileFeet = Integer.valueOf(height[0].trim()).intValue();
			profileInches = Integer.valueOf(height[1].trim()).intValue();
			profileGender = historyScan.nextLine().trim().charAt(0);
			profileIntake = Integer.valueOf(historyScan.nextLine().trim()).intValue();

			ArrayList<String> weightDates = new ArrayList<String>();
			ArrayList<Float> weightHistory = new ArrayList<Float>();
			while (historyScan.hasNextLine()) {
				String weightLine = historyScan.nextLine();
				if (weightLine.contains("weight")) {
					String[] weightSplit = weightLine.split("\\s+");
					weightDates.add(weightSplit[1]);
					weightHistory.add((Float.valueOf(weightSplit[2].trim()).floatValue()));
				}
				;
				profileWeights = new Weight(profileWeight, weightDates, weightHistory);
				historyScan.close();
			}
		} catch (FileNotFoundException ex) {
			ex.toString();
		}

		return new UserProfile(username, profileAge, (profileFeet * 12) + profileInches, profileWeight, profileGender,
				profileIntake);

	}
	
	//return a rounded double to a given precision
	private static double roundPrecision(double value, int precision) {
		int scale = (int) Math.pow(10, precision);
		return (double) Math.round(value * scale) / scale;
	}

	//main program; creates instance of program
	public static void main(String[] args) {
		Nutrition_Tracker tracker = new Nutrition_Tracker();
	}

}
