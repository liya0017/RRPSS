import java.util.Scanner;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class MenuItemModifier {
    
    public void addMenuItem(HashMap<String, ArrayList<MenuItem>> Menu){
        Scanner sc = new Scanner(System.in);
        int i=1;
        boolean flag = true;
        double itemPrice;
        Integer userInput;

        for (String itemname: Menu.keySet()){
            System.out.println(i + ". " + itemname);
            i++;
        }

        System.out.println("What type of item do you want to add?");
        while (true){
            try {
                userInput = Integer.parseInt(sc.nextLine());
                break; 
            } catch (NumberFormatException e){
                System.out.println("Error! Input is not a valid! Please enter a valid input!");
            }
        }

        System.out.println("Item name: ");
        String itemName = sc.nextLine();
        System.out.println("Item description:");
        String itemDescription = sc.nextLine();
        System.out.println("Item price:");
        
        while (true){
            try {
                itemPrice = Double.parseDouble(sc.nextLine());
                break; 
            } catch (NumberFormatException e){
                System.out.println("Error! Input is not a valid price! Please enter a valid price!");
            }
        }

        switch (userInput){
            case 1:
                Menu.get("MainCourse").add(new MainCourse(itemName, itemDescription, itemPrice));
                break;
            case 2:
                Menu.get("Drink").add(new Drink(itemName, itemDescription, itemPrice));
                break;
            case 3:
                Menu.get("Dessert").add(new Dessert(itemName, itemDescription, itemPrice));
                break;
            default:
                System.out.println("Item type does not exist! Please choose within the range of available item types.");
                flag = false;
                break;
        }

        if (flag == true){
            System.out.println("Item added successfully!");
        }
    }

    public void updateMenuItem(HashMap<String, ArrayList<MenuItem>> Menu, String itemType, int itemNum){
        Scanner sc = new Scanner(System.in);
        int userInput;
        
        System.out.println("What do you want to change?");
        System.out.println("1. Name");
        System.out.println("2. Description");
        System.out.println("3. Price");
        while (true){
            try {
                userInput = Integer.parseInt(sc.nextLine());
                break; 
            } catch (NumberFormatException e){
                System.out.println("Error! Input is not a valid! Please enter a valid input!");
            }
        }

        MenuItem toChange = Menu.get(itemType).get(itemNum-1);
        
        switch (userInput){
            case 1:
                System.out.println("Enter new name: ");
                String newName = sc.nextLine();
                toChange.setName(newName);
                break;
            case 2:
                System.out.println("Enter new description: ");
                String newDescription = sc.nextLine();
                toChange.setDescription(newDescription);
                break;
            case 3:
                System.out.println("Enter new price: ");
                try {
                    double newPrice = Double.parseDouble(sc.nextLine());
                    toChange.setPrice(newPrice);
                    break; 
                } catch (NumberFormatException e){
                    System.out.println("Error! Input is not a valid price!");
                }
                break;
            default:
                System.out.println("Error! Invalid option was chosen!");
        }
    }

    public void deleteMenuItem(HashMap<String, ArrayList<MenuItem>> Menu, String itemType, int itemNum){
        Menu.get(itemType).remove(itemNum-1);
    }
}
