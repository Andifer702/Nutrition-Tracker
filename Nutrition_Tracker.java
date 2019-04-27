/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nutrition_tracker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.EtchedBorder;
import javax.swing.text.*;
import org.jfree.chart.ChartPanel;

/**
 *
 * @author Andrew
 */
public class Nutrition_Tracker {

    /**
     * @param args the command line arguments
     */
    //Variable Declaration
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

    private BufferedWriter writer;

    //Constructor
    public Nutrition_Tracker() {
        buildLoginGUI();
    }

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

        JPanel loginTab = new JPanel();
        loginPane.addTab("Login", null, loginTab, null);

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

        String[] genderList = {"Male", "Female"};
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

        rdbtnCalculateCalorieIntake.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //calField.setEditable(false);
                calField.setEnabled(false);
            }
        });

        rdbtnEnterCalorieIntake.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //calField.setEditable(true);
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
                if (inchInput <= 0) {
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
                    if (genderBox.equals(genderList[0])) {
                        genChar = 'M';
                    } else {
                        genChar = 'F';
                    }

                    if (rdbtnCalculateCalorieIntake.isSelected()) {
                        currentUser = new UserProfile(userInput, ageInput, totalHeight, weightInput, genChar);
                    } else if (rdbtnEnterCalorieIntake.isSelected()) {
                        currentUser = new UserProfile(userInput, ageInput, totalHeight, weightInput, genChar, calInput);
                    }
                    buildGUI();
                    frame.setVisible(false);
                }
            }
        });

        frame.setVisible(true);
    }

    private void buildGUI() {
        mainFrame = new JFrame("Nutrition Tracker");
        mainFrame.setSize(450, 600);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {

                System.exit(0);
            }
        });

        //hardcoding user information
        //default calorie built for testing
        tabPane = new JTabbedPane();

        //Build Main Page GUI
        summaryPanel = new JPanel(new BorderLayout());
        messageText = new JTextPane();
        StyledDocument doc = messageText.getStyledDocument();
        Date date = new Date();
        String formattedDate = String.format("%tD", date);

        createFile();

        //defining text styles
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

        //inserting messages
        try {
            doc.insertString(doc.getLength(), "\n\nWelcome " + currentUser.getUsername() + "!" + "\n", headingStyle);
            doc.insertString(doc.getLength(), "Today is: " + formattedDate + "\n\n", headingStyle);

            doc.insertString(doc.getLength(), "You have consumed " + currentUser.getCalInfo().getDailyCalorieCount() + " out of "
                    + currentUser.getCalInfo().getCalorieIntake() + " calories\n", paraStyle);
            doc.insertString(doc.getLength(), "And drank " + currentUser.getWaterInfo().getWater() + " oz of water", paraStyle);

        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        //Add Main GUI panel to the 1st tab
        summaryPanel.add(messageText, BorderLayout.CENTER);
        tabPane.addTab("Main", summaryPanel);

        //Build AddFood GUI
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

        //add components to the top panel
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

        //add scrollable textpane in the center
        foodSummaryText = new JTextArea();
        foodSummaryText.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        foodSummaryText.setEditable(false);

        foodSummaryPane = new JScrollPane(foodSummaryText);
        foodSummaryPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        foodSummaryPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        foodSummaryText.append("Food consumption summary message will go here\n");

        addPanel.add(inputFoodPanel, BorderLayout.NORTH);
        addPanel.add(foodSummaryPane, BorderLayout.CENTER);

        //add AddFood GUI to the 2nd tab
        tabPane.addTab("Add Nutrition", addPanel);

        //Build Graph GUI
        graphPanel = new JPanel(new BorderLayout());
        userGraph = new GraphUserInfo(currentUser);

        chartPanel = new ChartPanel(userGraph.calorieGraph);

        graphPanel.add(chartPanel, BorderLayout.CENTER);

        //add Graph GUI to the 3rd tab
        tabPane.addTab("Graph", graphPanel);

        //add tabPane to mainFrame
        mainFrame.add(tabPane);
        mainFrame.setVisible(true);

        //Button action for inputting food data
        inputFoodButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //input food name and calorie information
                String foodInput = foodTextfield.getText().toLowerCase();

                //check to see if the calorie input is an integer
                if (isInteger(caloriesTextfield.getText())) {
                    int calorieInput = Integer.parseInt(caloriesTextfield.getText());

                    //update userCalorie and updated food summary text panel
                    currentUser.getCalInfo().addFood(foodInput, calorieInput);
                    foodSummaryText.append("You ate " + foodInput + ". Caloric value: " + calorieInput + "\n");

                    //formats the string then write to file
                    foodInput = foodInput.replaceAll(" ", "_");
                    writeToFile(formattedDate + " " + foodInput + " " + caloriesTextfield.getText());
                } else {
                    JOptionPane.showMessageDialog(null, "Please input integer for calories");
                }

                foodTextfield.setText("");
                caloriesTextfield.setText("");

                //update Summary Panel
                messageText.setText("");
                try {
                    doc.insertString(doc.getLength(), "\n\nWelcome " + currentUser.getUsername() + "\n", headingStyle);
                    doc.insertString(doc.getLength(), "Today is: " + formattedDate + "\n\n", headingStyle);

                    doc.insertString(doc.getLength(), "You have consumed " + currentUser.getCalInfo().getDailyCalorieCount() + " calories\n", paraStyle);
                    doc.insertString(doc.getLength(), "And drank " + currentUser.getWaterInfo().getWater() + " oz of water", paraStyle);

                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }

            }
        });

        //button action for inputting water intake
        inputWaterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //input amount of water consumed
                if (isDouble(waterAmount.getText())) {
                    double waterInput = Double.parseDouble(waterAmount.getText());
                    currentUser.waterInfo.addWater(waterInput);
                    foodSummaryText.append("You drank " + waterInput + " oz of water.\n");
                    writeToFile(formattedDate + " water " + waterAmount.getText());
                } else {
                    JOptionPane.showMessageDialog(null, "Please use integer/double for water drank");
                }

                waterAmount.setText("");

                //Update Summary Panel
                messageText.setText("");
                try {
                    doc.insertString(doc.getLength(), "\n\nWelcome " + "<Username Here>!" + "\n", headingStyle);
                    doc.insertString(doc.getLength(), "Today is: " + formattedDate + "\n\n", headingStyle);

                    doc.insertString(doc.getLength(), "You have consumed " + currentUser.getCalInfo().getDailyCalorieCount() + " calories\n", paraStyle);
                    doc.insertString(doc.getLength(), "And drank " + currentUser.getWaterInfo().getWater() + " oz of water", paraStyle);

                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        });

        //button action for search
        search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //perform search for food
                try {
                    //perform search for food
                    String foodSearch = foodTextfield.getText();
                    foodDatabase = new FoodDatabase(foodSearch);
                    StringBuilder sb = new StringBuilder();
                    sb = foodDatabase.getFoodResult();
                    if (sb.length() != 0) {
                        foodSummaryText.append(sb.toString());
                    } else {
                        JOptionPane.showMessageDialog(null, "There are no " + foodSearch + " in the food database.");
                    }
                    foodTextfield.setText("");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });

        //button action for clearing input data
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                foodTextfield.setText("");
                caloriesTextfield.setText("");
                waterAmount.setText("");
            }
        });

    }

    //checking user input for calories
    public boolean isInteger(String string) {
        try {
            Integer.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //checking user input for water drank
    public boolean isDouble(String string) {
        try {
            Double.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void createFile() {
        File file = new File(currentUser.getUsername() + ".txt");

        try {
            if (!file.exists()) {
                file.createNewFile();
            } else {
                System.out.println("File already exists");
            }
            writer = new BufferedWriter(new FileWriter(file, true));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error creating file");
        }

    }

    public void writeToFile(String str) {
        try {
            writer.write(str);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.toString();
        }

    }

    public int parseFile(String fileName, String keyword) {
        int result = 0;
        try {
            Scanner scan = new Scanner(new File(fileName));
            while (scan.hasNext()) {
                String line = scan.nextLine().toLowerCase().toString();
                if (line.contains(keyword)) {
                    result += 1;
                }
            }
        } catch (FileNotFoundException ex) {
            ex.toString();
        }
        return result;
    }

    public static void main(String[] args) {
        // TODO code application logic here
        Nutrition_Tracker tracker = new Nutrition_Tracker();
    }

}
