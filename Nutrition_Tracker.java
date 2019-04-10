/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nutrition_tracker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
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
    private JTextField foodName;
    private JTextField calories;
    private JButton inputFoodButton;
    private JButton clearButton;
    private JLabel water;
    private JTextField waterAmount;
    private JButton inputWaterButton;
    private JLabel waterUnit;
    private JLabel blank;

    
    private Date date;

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

        tabPane = new JTabbedPane();

        //Build Main Page GUI
        summaryPanel = new JPanel(new BorderLayout());
        messageText = new JTextPane();
        StyledDocument doc = messageText.getStyledDocument();
        Date date = new Date();
        String str = String.format("%tD", date);
        
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
            doc.insertString(doc.getLength(), "Today is: " + str + "\n\n", headingStyle);

            doc.insertString(doc.getLength(), "You have consumed " + "<calorie here>" + " calories\n", paraStyle);
            doc.insertString(doc.getLength(), "And drank " + "<oz/ml of water>" + " of water", paraStyle);

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
        foodName = new JTextField();
        foodName.setPreferredSize(new Dimension(100, 25));
        calories = new JTextField();
        calories.setPreferredSize(new Dimension(60, 25));
        inputFoodButton = new JButton("Submit");
        
        water = new JLabel("Water: ");
        waterAmount = new JTextField();
        waterAmount.setPreferredSize(new Dimension(40, 25));
        waterUnit = new JLabel("ml ");
        inputWaterButton = new JButton("Submit");
        
        blank = new JLabel("                     ");
        clearButton = new JButton("Clear");
        
        //add components to the top panel
        inputFoodPanel.add(food);
        inputFoodPanel.add(foodName);
        inputFoodPanel.add(cal);
        inputFoodPanel.add(calories);
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
        
        foodSummaryText.append("Food consumption summary message will go here");
        
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
        
        //Button action events
        inputFoodButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //input food name and calorie information
            }
        });
        
        inputWaterButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //input amount of water consumed
            }
        });
        
        clearButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                foodName.setText("");
                calories.setText("");
                waterAmount.setText("");
            }
        });
        
    }

    public static void main(String[] args) {
        // TODO code application logic here
        Nutrition_Tracker tracker = new Nutrition_Tracker();
    }

}
