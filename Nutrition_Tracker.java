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
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.EtchedBorder;
import javax.swing.text.*;

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
    private JLabel water;
    private JTextField waterAmount;
    private JButton inputWaterButton;
    private JLabel waterUnit;
    private JLabel blank;

    private Date date;
    private Calories userCalorie;
    private Water userWater;

    private BufferedWriter writer;

    //Constructor
    public Nutrition_Tracker() {
        buildGUI();
    }

    private void buildGUI() {
        mainFrame = new JFrame("Nutrition Tracker");
        mainFrame.setSize(400, 600);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                
                System.exit(0);
            }
        });

        //hardcoding user information
        //default calorie built for testing
        userCalorie = new Calories(2500);
        userWater = new Water();

        tabPane = new JTabbedPane();

        //Build Main Page GUI
        summaryPanel = new JPanel(new BorderLayout());
        messageText = new JTextPane();
        StyledDocument doc = messageText.getStyledDocument();
        Date date = new Date();
        String formattedDate = String.format("%tD", date);
        
        createFile();
        if(parseFile("Eat_History.txt", formattedDate)==0){
            writeToFile(formattedDate);
        }
        
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
            doc.insertString(doc.getLength(), "\n\nWelcome " + "<Username Here>!" + "\n", headingStyle);
            doc.insertString(doc.getLength(), "Today is: " + formattedDate + "\n\n", headingStyle);

            doc.insertString(doc.getLength(), "You have consumed " + userCalorie.getDailyCalorieCount() + " calories\n", paraStyle);
            doc.insertString(doc.getLength(), "And drank " + userWater.getWater() + " oz of water", paraStyle);

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

        water = new JLabel("Water: ");
        waterAmount = new JTextField();
        waterAmount.setPreferredSize(new Dimension(40, 25));
        waterUnit = new JLabel("oz ");
        inputWaterButton = new JButton("Submit");

        blank = new JLabel("                     ");
        clearButton = new JButton("Clear");

        //add components to the top panel
        inputFoodPanel.add(food);
        inputFoodPanel.add(foodTextfield);
        inputFoodPanel.add(cal);
        inputFoodPanel.add(caloriesTextfield);
        inputFoodPanel.add(inputFoodButton);
        inputFoodPanel.add(water);
        inputFoodPanel.add(waterAmount);
        inputFoodPanel.add(waterUnit);
        inputFoodPanel.add(blank);
        inputFoodPanel.add(inputWaterButton);
        inputFoodPanel.add(blank);
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
        JLabel label = new JLabel("Placeholder for Graph");
        graphPanel.add(label, BorderLayout.CENTER);

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
                    userCalorie.addFood(foodInput, calorieInput);
                    foodSummaryText.append("You ate " + foodInput + ". Caloric value: " + calorieInput + "\n");
                    
                    //formats the string then write to file
                    foodInput = foodInput.replaceAll(" ", "_");
                    writeToFile(foodInput + " " + caloriesTextfield.getText());
                } else {
                    JOptionPane.showMessageDialog(null, "Please input integer for calories");
                }

                foodTextfield.setText("");
                caloriesTextfield.setText("");

                //update Summary Panel
                messageText.setText("");
                try {
                    doc.insertString(doc.getLength(), "\n\nWelcome " + "<Username Here>!" + "\n", headingStyle);
                    doc.insertString(doc.getLength(), "Today is: " + formattedDate + "\n\n", headingStyle);

                    doc.insertString(doc.getLength(), "You have consumed " + userCalorie.getDailyCalorieCount() + " calories\n", paraStyle);
                    doc.insertString(doc.getLength(), "And drank " + userWater.getWater() + " oz of water", paraStyle);

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
                    userWater.addWater(waterInput);
                    foodSummaryText.append("You drank " + waterInput + " oz of water.\n");
                    writeToFile("water " + waterAmount.getText());
                } else {
                    JOptionPane.showMessageDialog(null, "Please use integer/double for water drank");
                }

                waterAmount.setText("");

                //Update Summary Panel
                messageText.setText("");
                try {
                    doc.insertString(doc.getLength(), "\n\nWelcome " + "<Username Here>!" + "\n", headingStyle);
                    doc.insertString(doc.getLength(), "Today is: " + formattedDate + "\n\n", headingStyle);

                    doc.insertString(doc.getLength(), "You have consumed " + userCalorie.getDailyCalorieCount() + " calories\n", paraStyle);
                    doc.insertString(doc.getLength(), "And drank " + userWater.getWater() + " oz of water", paraStyle);

                } catch (BadLocationException ex) {
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
        File file = new File("Eat_History.txt");

        try {
            if (!file.exists()){
                file.createNewFile();
            }else{
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
