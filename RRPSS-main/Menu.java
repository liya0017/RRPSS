import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

class Menu {
    private HashMap<String, ArrayList<MenuItem>> myMenu;
    private ArrayList<Promotional> promoMenu;
    private MenuReader myReader;
    private MenuItemModifier menuMod;
    
    Menu(){
    	this.promoMenu = new ArrayList<Promotional>();
        this.myReader = new MenuReader();
        this.myMenu = this.myReader.readMenuFile();
        this.menuMod = new MenuItemModifier();
    }

    void addMenuItem(){
        this.menuMod.addMenuItem(this.myMenu);
    }

    void updateMenuItem(){
        boolean flag = true;
        Scanner sc = new Scanner(System.in);
        int i=1;
        int itemNum, itemType;
        String holder = "";
        System.out.println("What type of item do you want to modify?");

        for (String temp : myMenu.keySet()){
            System.out.println(i + ". " + temp);
            i++;
        }

        while (true){
            try {
                itemType = Integer.parseInt(sc.nextLine());
                break; 
            } catch (NumberFormatException e){
                System.out.println("Error! Input is not a valid! Please enter a valid input!");
            }
        }

        switch (itemType){
            case 1:
                if (getNumMains() == 0){
                    flag = false;
                    System.out.println("No such items to to update!");
                    break;
                }
                getMains();
                holder = "MainCourse";
                break;
            case 2:
                if (getNumDrinks() == 0){
                    flag = false;
                    System.out.println("No such items to to update!");
                    break;
                }
                getDrinks();
                holder = "Drink";
                break;
            case 3:
                if (getNumDessert() == 0){
                    flag = false;
                    System.out.println("No such items to to update!");
                    break;
                }
                getDesserts();
                holder = "Dessert";
                break;
            default:
                System.out.println("Error! Invalid type of item chosen");
                flag = false;
                break;
        }

        if (flag == true){
            System.out.println("Which item do you want to modify?");
            while (true){
                try {
                    itemNum = Integer.parseInt(sc.nextLine());
                    break; 
                } catch (NumberFormatException e){
                    System.out.println("Error! Input is not a valid! Please enter a valid input!");
                }
            }

            if (itemNum > myMenu.get(holder).size() || itemNum <= 0){
                System.out.println("Error! Invalid item was chosen!");
            } else {
                this.menuMod.updateMenuItem(this.myMenu, holder, itemNum);
            }
        }
    }

    void deleteMenuItem(){
        boolean flag = true;
        Scanner sc = new Scanner(System.in);
        int i=1;
        int itemNum, itemType;
        String holder = "";

        System.out.println("What type of item do you want to delete?");
        for (String temp : myMenu.keySet()){
            System.out.println(i + ". " + temp);
            i++;
        }

        while (true){
            try {
                itemType = Integer.parseInt(sc.nextLine());
                break; 
            } catch (NumberFormatException e){
                System.out.println("Error! Input is not a valid! Please enter a valid input!");
            }
        }

        switch (itemType){
            case 1:
                if (getNumMains() == 0){
                    flag = false;
                    System.out.println("No such items to to remove!");
                    break;
                }
                getMains();
                holder = "MainCourse";
                break;
            case 2:
                if (getNumDrinks() == 0){
                    flag = false;
                    System.out.println("No such items to to remove!");
                    break;
                }
                getDrinks();
                holder = "Drink";
                break;
            case 3:
                if (getNumDessert() == 0){
                    flag = false;
                    System.out.println("No such items to to remove!");
                    break;
                }
                getDesserts();
                holder = "Dessert";
                break;
            default:
                System.out.println("Error! Invalid type of item chosen!");
                flag = false;
                break;
        }

        if (flag == true){
            System.out.println("Which item do you want to delete?");
            while (true){
                try {
                    itemNum = Integer.parseInt(sc.nextLine());
                    break; 
                } catch (NumberFormatException e){
                    System.out.println("Error! Input is not a valid! Please enter a valid input!");
                }
            }

            if (itemNum > myMenu.get(holder).size() || itemNum <= 0){
                System.out.println("Error! Invalid item was chosen!");
            } else {
                this.menuMod.deleteMenuItem(this.myMenu, holder, itemNum);
                System.out.println("After update,");
                switch (holder){
                    case "MainCourse":
                        getMains();
                        break;
                    case "Drink":
                        getDrinks();
                        break;
                    case "Dessert":
                        getDesserts();
                        break;
                }
            }
        }
    }
    
    //DONE
    void addPromotionalItem(){  // Add menu items to a set, add set to promotionalitem
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name for promotion: ");
        String promoname = sc.nextLine();
        Promotional temp = new Promotional(promoname);
        System.out.println("How many items would you like in the promotion?");
        int numOfItems = RRPSS.getInput(1,Integer.MAX_VALUE);

        for (int i = 0; i < numOfItems; i++) {
            System.out.println("What type of item do you want to add to the promotion?");
            showItemTypes();
            int userInput = RRPSS.getInput(1,Integer.MAX_VALUE);
            switch (userInput){
            	case 1:
	            	System.out.println("Which Main item would you like to add?");
	            	getMains();
	            	int itemNumber = RRPSS.getInput(1,myMenu.get("MainCourse").size());
	                if (itemNumber < 1 || itemNumber > myMenu.get("MainCourse").size()){
	                    System.out.println("Invalid item!");
                        i--;
	                    break;
	                }
	                temp.addItem(myMenu.get("MainCourse").get(itemNumber-1));
	                break;
               	case 2:
	            	System.out.println("Which Drink item would you like to add?");
	            	getDrinks();
	            	itemNumber = RRPSS.getInput(1,myMenu.get("Drink").size());
	                if (itemNumber < 1 || itemNumber > myMenu.get("Drink").size()){
	                    System.out.println("Invalid item!");
                        i--;
	                    break;
	                }
	                temp.addItem(myMenu.get("Drink").get(itemNumber-1));
	                break;
             	case 3:
	            	System.out.println("Which Dessert item would you like to add?");
	            	getDesserts();
	            	itemNumber = RRPSS.getInput(1,myMenu.get("Dessert").size());
	                if (itemNumber < 1 || itemNumber > myMenu.get("Dessert").size()){
	                    System.out.println("Invalid item!");
                        i--;
	                    break;
	                }
	                temp.addItem(myMenu.get("Dessert").get(itemNumber-1));
	                break;
	            default:
	            	System.out.println("Error!");
	            	break;
	                	
            }	
        }
        temp.printItems();
        System.out.println("Total price for promotion: " + temp.getPrice());
        this.promoMenu.add(temp);
    }
    
    
    

	void updatePromotionalItem(){
    	 
    	 boolean continueFlag = true;
         if (this.promoMenu.size() == 0){
             System.out.println("No promotions to update!");
             return;
         }
         Scanner sc = new Scanner(System.in);
         System.out.println("Which promotion item do you want to update?");
         getPromotions();
         int itemNumber = RRPSS.getInput(1, promoMenu.size());
         if (itemNumber < 1 || itemNumber > promoMenu.size()){  // Pick promo item to update
             System.out.println("Invalid item!");
             return;
         }
         Promotional toUpdate = promoMenu.get(itemNumber-1);
         // Add or Remove items from promo bundle
         do {
        	 toUpdate.printItems();
        	 System.out.println("Do you want to add or remove items from the promo?");
        	 System.out.println("1. Add\n2. Remove\n3. Exit");
        	 int addOrRemove = RRPSS.getInput(1,3);
        	 switch (addOrRemove) {
        	 case 1:  // Add
        		 System.out.println("What type of item do you want to add to the promotion?");
                 showItemTypes();
                 int userInput = RRPSS.getInput(1,3);
                 switch (userInput){
                 	case 1:
     	            	System.out.println("Which Main item would you like to add?");
     	            	getMains();
     	            	userInput = RRPSS.getInput(1,myMenu.get("MainCourse").size());
     	                if (itemNumber < 1 || itemNumber > myMenu.get("MainCourse").size()){
     	                    System.out.println("Invalid item!");
     	                    break;
     	                }
     	                toUpdate.addItem(myMenu.get("MainCourse").get(itemNumber-1));
     	                break;
                    case 2:
                        System.out.println("Which Drink item would you like to add?");
                        getDrinks();
     	            	userInput = RRPSS.getInput(1,myMenu.get("Drink").size());
                        if (itemNumber < 1 || itemNumber > myMenu.get("Drink").size()){
                            System.out.println("Invalid item!");
                            break;
     	                }
     	                toUpdate.addItem(myMenu.get("Drink").get(itemNumber-1));
     	                break;
                  	case 3:
     	            	System.out.println("Which Dessert item would you like to add?");
     	            	getDesserts();
     	            	userInput = RRPSS.getInput(1,myMenu.get("Dessert").size());
     	                if (itemNumber < 1 || itemNumber > myMenu.get("Dessert").size()){
     	                    System.out.println("Invalid item!");
     	                    break;
     	                }
     	                toUpdate.addItem(myMenu.get("Dessert").get(itemNumber-1));
     	                break;
     	            default:
     	            	System.out.println("Error!");
     	            	break;
                 }
                 break;
        	 case 2:  // Remove item from Promo bundle
        		 toUpdate.printItems();
        		 System.out.println("Which item would you like to remove?");
        		 int removeItem = RRPSS.getInput(1, toUpdate.getNumOfItems());
        	      if (removeItem < 1 || removeItem > toUpdate.getNumOfItems()){
	                    System.out.println("Invalid item!");
                        return;
	                }
        	      toUpdate.removeItem(removeItem-1);
        	      toUpdate.printItems();
        		 break;
        	 case 3:
        		 continueFlag = false;
        		 System.out.println("\n****Final Promo Entry****\n");
        		 toUpdate.printItems();
    	         System.out.println("Total price for promotion: " + toUpdate.getPrice());
    	         System.out.println(" ");
        		 System.out.println("Exiting...");
        		 break;
        	 }
         }while (continueFlag == true);
     }
    
     
    void deletePromotionalItem(){
    	 		 boolean continueFlag = true;
    	 		 
    	 		 do {
    	 			  if (promoMenu.size() == 0){
    	    	             System.out.println("No promotions to update!");
    	    	             continueFlag = false;
    	    	             return;
    	    	         }
    	 			 Scanner sc = new Scanner(System.in);
        	         System.out.println("Which promotion item do you want to delete?");
        	         getPromotions();
        	         int removeIndex = RRPSS.getInput(1, promoMenu.size());
                     if (removeIndex < 1 || removeIndex > promoMenu.size()){
                         System.out.println("Invalid item!");
                         return;
                     }
                     promoMenu.remove(removeIndex-1);
    	 		 } while (continueFlag == true);
     }
                    
    //DONE
    void getMains(){
        int i = 1;
        System.out.println();
        System.out.println("***Mains in the menu");
        for (MenuItem tempObject : myMenu.get("MainCourse")){
                MainCourse temp = (MainCourse) tempObject;
                System.out.println(i + ". " + temp.getName());
                System.out.println("Description: " + temp.getDescription());
                System.out.println("Price: " + temp.getPrice());
                System.out.println();
                i++;
        }
    }
    
    //DONE
    void getDrinks(){
        int i = 1;
        System.out.println();
        System.out.println("***Drinks in the menu");
        for (MenuItem tempObject : myMenu.get("Drink")){
                Drink temp = (Drink) tempObject;
                System.out.println(i + ". " + temp.getName());
                System.out.println("Description: " + temp.getDescription());
                System.out.println("Price: " + temp.getPrice());
                System.out.println();
                i++;
        }
    }
    
    //DONE
    void getDesserts(){
        int i = 1;
        System.out.println();
        System.out.println("***Desserts in the menu");
        for (MenuItem tempObject : myMenu.get("Dessert")){
                Dessert temp = (Dessert) tempObject;
                System.out.println(i + ". " + temp.getName());
                System.out.println("Description: " + temp.getDescription());
                System.out.println("Price: " + temp.getPrice());
                System.out.println();
                i++;
        }
    }
    
    public void getPromotions(){ 
        if (this.promoMenu.size() == 0){ 
            System.out.println("No promotions found!"); 
        } else { 
            for (int i =0; i<this.promoMenu.size(); i++){ 
                System.out.println("Combo " + (i+1) + ": "+ this.promoMenu.get(i).getName()); 
                System.out.println("-----------------------------"); 
                this.promoMenu.get(i).printItems(); 
            } 
        } 
    }
    
    
    void showItemTypes(){
        System.out.println("1. MainCourse");
        System.out.println("2. Drink");
        System.out.println("3. Dessert");
    }
    
    void showItemParameters(){
        System.out.println("1. Name");
        System.out.println("2. Description");
        System.out.println("3. Price");
    }

    int getNumMains(){
        return myMenu.get("MainCourse").size();
    }

    int getNumDrinks(){
        return myMenu.get("Drink").size();
    }

    int getNumDessert(){
        return myMenu.get("Dessert").size();
    }

    int getNumPromotion(){
        return promoMenu.size();
    }

    HashMap<String, ArrayList<MenuItem>> getHashMenu(){
        return this.myMenu;
    }

    ArrayList<Promotional> getPromoArrayList(){
        return this.promoMenu;
    }
    
    void outputNewMenuFile(){
        try {
            File myFile = new File("MenuItems.txt");
            if (myFile.createNewFile()){
                System.out.println("File created: " + myFile.getName());
            } else {
                System.out.println("Menu has been updated to the latest version.");
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        
        try {
            FileWriter myWriter = new FileWriter("MenuItems.txt");
            
            for (String itemType : myMenu.keySet()){
                for (MenuItem curitem : myMenu.get(itemType)){
                    myWriter.write(itemType);
                    myWriter.write("\r\n");
                    myWriter.write(curitem.getName());
                    myWriter.write("\r\n");
                    myWriter.write(curitem.getDescription());
                    myWriter.write("\r\n");
                    myWriter.write(String.valueOf(curitem.getPrice()));
                    myWriter.write("\r\n");
                    myWriter.write("\r\n");
                }
            }
            
            myWriter.write("END");
            myWriter.write("\r\n");
            myWriter.close();
            
        } catch (IOException e){
            System.out.println(e.getMessage());
        }   
    }
}
