/*
 * Graphs a user's calorie information. using external library jfree.chart
 * 
 * @authors Bre Chung, Dewayne Edwards, Jacob Heddings, Renan Jorge, Joon Park
 */
package nutrition_tracker;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
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
    Date currentDate;
    String formattedDate;
    String username;
    String date;
    String date2;
    int calories;
    LocalDate pastDate;
    long daysDifference;
    //ArrayList<Integer> calorieHistory;
    int calorieHistory[];

    public GraphUserInfo(UserProfile currentUser) {
        username = currentUser.getUsername();
        dataset = new DefaultCategoryDataset();
        readFile();
        for (int i = 0; calorieHistory.length > i; i++) {
            if (calorieHistory[i] != 0) {
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
            //This is the date formatter for parsing the date in specific patter
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
            
            BufferedReader calorieFile = new BufferedReader(new FileReader(username + "_eatHistory.txt"));

            //Setting up today's date in the right format to compare with the dates in the txt file
            currentDate = new Date();
            formattedDate = String.format("%tD", currentDate);
            LocalDate today = LocalDate.parse(formattedDate, formatter);
            
            //reading the file
            Scanner sc = new Scanner(calorieFile);
            while (sc.hasNextLine()) {
                String dateEntry = sc.nextLine().trim();
                Scanner line = new Scanner(dateEntry);

                if (line.hasNext()) {
                    //check only for food entries
                    if (line.next().equals("food")) {
                        if (date == null) {
                            date = line.next();
                            //create date object using the date read
                            //if the date difference between the past date and today's date is over 30 days,
                            //do not add to graph
                            pastDate = LocalDate.parse(date, formatter);
                            daysDifference = ChronoUnit.DAYS.between(pastDate, today);
                            if (daysDifference > 30) {
                                continue;
                            }
                        } else {
                            //date2 is used to compare the date and date2.
                            //if date2 is equal to date, calorie gets added to the same index
                            //if date2 is a new date, calories are added to the new index
                            
                            date2 = line.next();
                            //checking the date difference with the next date read
                            pastDate = LocalDate.parse(date2, formatter);
                            daysDifference = ChronoUnit.DAYS.between(pastDate, today);
                            if (daysDifference > 30) {
                                continue;
                            }
                            
                            if (!date2.equals(date)) {
                                date = date2;
                                i += 1; //increase i to select the next index for new dates
                            }
                        }
                        line.next();

                        calories = Integer.parseInt(line.next());
                        calorieHistory[i] += calories;
                    }
                }

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(GraphUserInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
