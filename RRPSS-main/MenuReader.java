
import java.util.Scanner;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.InputStream;

public class MenuReader {

    public HashMap<String, ArrayList<MenuItem>> readMenuFile(){
        HashMap<String, ArrayList<MenuItem>> tempMenu = new HashMap<String, ArrayList<MenuItem>>();
        Scanner filescanner = new Scanner(System.in);
        String filename = "MenuItems.txt";
		InputStream is = this.getClass().getResourceAsStream(filename);
        
        try (Scanner filescan = new Scanner(is)){
            ArrayList<String> fileLines = new ArrayList<>();
            
            while (filescan.hasNextLine()){
                String line = filescan.nextLine();
                
                if (line.equals("END")){
                    break;
                }
                
                if (line.isEmpty() || !filescan.hasNextLine()){
                    String itemType = fileLines.get(0);
                    String itemName = fileLines.get(1);
                    String itemDescription = fileLines.get(2);
                    double itemPrice = Double.valueOf(fileLines.get(3));
                    
                    switch(itemType){
                        case "MainCourse":
                            if (!(tempMenu.containsKey(itemType))){
                                tempMenu.put(itemType, new ArrayList<MenuItem>());
                            }
                            tempMenu.get(itemType).add(new MainCourse(itemName, itemDescription, itemPrice));
                            break;
                        case "Drink":
                            if (!(tempMenu.containsKey(itemType))){
                                tempMenu.put(itemType, new ArrayList<MenuItem>());
                            }
                            tempMenu.get(itemType).add(new Drink(itemName, itemDescription, itemPrice));
                            break;
                        case "Dessert":
                            if (!(tempMenu.containsKey(itemType))){
                                tempMenu.put(itemType, new ArrayList<MenuItem>());
                            }
                            tempMenu.get(itemType).add(new Dessert(itemName, itemDescription, itemPrice));
                            break;
                        default:
                            System.out.println("Error in item type");
                    }
                    fileLines.clear();
                } else {
                    fileLines.add(line);
                }
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return tempMenu;
    }
}
