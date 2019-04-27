/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nutrition_tracker;

import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Andrew
 */
public class GraphUserInfo {

    DefaultCategoryDataset dataset;
    JFreeChart calorieGraph;
    
    String username;
    String date;
    String date2;
    int calories;
    int calorieHistory[];

    public GraphUserInfo(UserProfile currentUser) {
    	username = currentUser.getUsername();
        dataset = new DefaultCategoryDataset();
        readFile();
        for (int i = 0; calorieHistory.length > i; i++) {
            if(calorieHistory[i] != 0){
                dataset.addValue(calorieHistory[i], "calories", "day " + i);
            }           
        }

        calorieGraph = ChartFactory.createLineChart(
                "User Calorie",
                "Dates",
                "Calories",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
    }

    private void readFile() {
        try {
            calorieHistory = new int[30];
            int i = 0;
            BufferedReader calorieFile = new BufferedReader(new FileReader(username + ".txt"));

            Scanner sc = new Scanner(calorieFile);
            while (sc.hasNextLine()) {
                String dateEntry = sc.nextLine().trim();
                Scanner line = new Scanner(dateEntry);

                if (date == null) {
                    date = line.next();
                } else {
                    date2 = line.next();
                    if(!date2.equals(date)){
                        date = date2;
                        i += 1;
                    }
                }

                if (!line.next().equals("water")) {
                    calories = Integer.parseInt(line.next());
                    calorieHistory[i] += calories;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GraphUserInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
