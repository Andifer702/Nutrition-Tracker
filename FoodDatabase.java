package nutrition_tracker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FoodDatabase { 

    private StringBuilder sb = new StringBuilder();

    public FoodDatabase (String foodSearch) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get("food.txt"))) {  
            stream
                    .filter(s -> s.toLowerCase().contains(foodSearch.toLowerCase()))
                    .map((s) -> {
                        sb.append(s);
                        return s;
                    }).forEachOrdered((_item) -> {
                        sb.append("\n");
                    });  
        }  
    }    
    
    public StringBuilder getFoodResult(){
        return this.sb;
    }    
}