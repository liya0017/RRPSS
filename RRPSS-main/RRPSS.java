import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.HashMap;
import java.text.NumberFormat;

public class RRPSS {

	//bootstart
	private static final Menu menu = new Menu();
	private static final StaffRoster roster = new StaffRoster();
	private static final SeatingManagement sm = new SeatingManagement();
	private static final ReservationList rl = new ReservationList();
	private static final ArrayList<Order> orders = new ArrayList<>();
	private static final HashMap<OrderItem, Integer> salesTracker = new HashMap<>();

	/**
	 * Helper function to get int input that is valid and within range from user
	 * @param min Minimum value
	 * @param max Maximum value
	 * @return input
	 */
	static int getInput(Integer min,Integer max){
		return getInput(min,max,null);
	}

	/**
	 * Helper function to get int input that is valid and within range from user
	 * @param min Minimum value
	 * @param max Maximum value
	 * @param outOfRangePrompt   Prompt to print if out of range, if null prints "Must be within the range of "+min+" and "+max+", try again"
	 * @return input
	 */
	static int getInput(Integer min,Integer max,String outOfRangePrompt){
		Scanner scanner = new Scanner(System.in);
		int choice = 0;
		do {
			try {
				choice = scanner.nextInt();
			}catch (InputMismatchException e){
				System.out.println("Invalid number, try again");
				scanner = new Scanner(System.in);// why infinite loop without this??
				continue;
			}
			if(choice < min || choice > max){
				System.out.println(outOfRangePrompt == null ? "Must be within the range of "+min+" and "+max+", try again" : outOfRangePrompt);
			}else
				break;
		}while (true);
		return choice;
	}

	public static void main(String[] args) {
		long rID;
		Scanner sc = new Scanner(System.in);
		roster.showStaff();
		System.out.println("Please identify yourself");
                int holderstaffinput = getInput(1, roster.getRosterSize());
		Staff staff = roster.getStaff(holderstaffinput-1);
		System.out.println("Welcome, "+staff.getName()+" ("+staff.getJobTitle()+")");
		while(true) {
			System.out.println();
			System.out.println("1. Edit Menu Items");
			System.out.println("2. Edit promotions");
			System.out.println("3. Create order");
			System.out.println("4. View order");
			System.out.println("5. Edit order");
			System.out.println("6. Create reservation");
			System.out.println("7. Check/Remove reservation");
			System.out.println("8. Check table availability");
			System.out.println("9. Print order invoice");
			System.out.println("10. Print sales revenue report");
			System.out.println("11. Exit");
			System.out.println("Enter choice");
			switch (getInput(1,11)){
				case 1:
					System.out.println("Editing menu");
					System.out.println("1. Add");
					System.out.println("2. Update");
					System.out.println("3. Remove");
					System.out.println("Enter choice");
					switch (getInput(1,3)){
						case 1: menu.addMenuItem();break;
						case 2: menu.updateMenuItem();break;
						case 3: menu.deleteMenuItem();break;
					}
					break;
				case 2:
					System.out.println("Editing promotions");
					System.out.println("1. Add");
					System.out.println("2. Update");
					System.out.println("3. Remove");
					System.out.println("Enter choice");
					switch (getInput(1,3)){
						case 1: menu.addPromotionalItem();break;
						case 2: menu.updatePromotionalItem();break;
						case 3: menu.deletePromotionalItem();break;
					}
					break;
				case 3:
					rl.removeInvalids(sm);
					createOrder(staff);
					break;
				case 4:
					System.out.println("Enter order ID");
					viewOrder(getInput(0,Integer.MAX_VALUE));
					break;
				case 5:
					System.out.println("Enter order ID");
					int orderID = getInput(0,Integer.MAX_VALUE);
					Order order = null;
					for(Order o : orders){
						if(o.getOrderID() == orderID){
							order = o;
							break;
						}
					}
					if(order == null){
						System.out.println("Invalid Order ID");
						break;
					}
					System.out.println("Editing order");
					System.out.println("1. Add item(s)");
					System.out.println("2. Remove item(s)");
					System.out.println("Enter choice");
					switch (getInput(1,2)){
						case 1: addItemToOrder(order);break;
						case 2: removeItemFromOrder(order);break;
					}
					break;
				case 6:
					rl.removeInvalids(sm);
					System.out.println("Enter customer name");
					String name = sc.next();
					System.out.println("Enter pax size");
					int pax = getInput(1,10);
					if(getAvailableTable(pax) != -1){
						createReservation(name,pax);
					} else {
						System.out.println("No available table");
					}
					break;
				case 7:
					//edit reservation
					System.out.println("Enter reservation ID: ");
					while (true){
						try {
							rID = Long.parseLong(sc.next());
							break; 
						} catch (NumberFormatException e){
							System.out.println("Error! Input is not a valid! Please enter a valid input!");
						}
					}
					rl.removeInvalids(sm);
					if(hasReservation(rID)){
						Reservation reservation = rl.getReservation(rID);
						System.out.println("Reservation of "+reservation.getPax()+" pax by "+reservation.getCustName());
						System.out.println(reservation.getReservationDate()+" "+reservation.getReservationTime());
						System.out.println("1. Remove");
						System.out.println("2. Back");
						if(getInput(1,2) == 1){
							removeReservation(rID);
						}
					}else
						System.out.println("Reservation has either expired or does not exist!");
					break;
				case 8:
					rl.removeInvalids(sm);
					sm.printTableStatus();
					System.out.println("Enter No. of pax");
					System.out.println(getAvailableTable(getInput(1,10)) != -1 ? "Table available" : "No available table");
					break;
				case 9:
					System.out.println("Enter reservation ID: ");
					orderID = getInput(0,Integer.MAX_VALUE);
					order = null;
					for(Order o : orders){
						if(o.getOrderID() == orderID){
							order = o;
							break;
						}
					}
					if(order == null){
						System.out.println("Invalid reservation ID");
						break;
					}
					printOrderInvoice(order);
					break;
				case 10:
					printSaleRevenue();
					break;
				case 11:
					FileCreator.outputReport(orders, salesTracker);
					menu.outputNewMenuFile();
					System.exit(0);
					return;
				default:
					System.out.println("Invalid choice");
					break;
			}
		}
	}
	//reservation

	static void createReservation(String name, int pax) {
		String memS;
		long phoneNum;
		Scanner scan = new Scanner(System.in);

		//check if table available
		// if yes then actually creates reservation , long phoneNum, Boolean mem
		int curTableID= getAvailableTable(pax);

		if(curTableID != -1){

			//get additional info
			System.out.println("Please enter phone number of customer: ");
			while (true){
				try {
					phoneNum = Long.parseLong(scan.nextLine());
					break; 
				} catch (NumberFormatException e){
					System.out.println("Error! Input is not a valid! Please enter a valid input!");
				}
			}
			System.out.println("Is the customer a member? (y/n)");

			while (true){
				memS = scan.next();
				if (memS.toLowerCase().charAt(0) == 'y' || memS.toLowerCase().charAt(0) == 'n'){
					break;
				}
				System.out.println("Please enter either y or n");
			}
			boolean mem = memS.toLowerCase().charAt(0) == 'y';
			System.out.println("Enter date of reservation in YYYY-MM-DD format");
			LocalDate date;
			LocalTime time;
			while(true){
				try {
					date = LocalDate.parse(scan.next());
					if (date.isBefore(LocalDate.now())){
						System.out.println("Please enter a valid date!");
					} else {
						break;
					}
				}catch (DateTimeParseException e){
					System.out.println("Invalid date format");
				}
			}

			System.out.println("Enter time of reservation in HH:MM (24H format)");
			while(true){
				try {
					time = LocalTime.parse(scan.next());
					if ((LocalTime.now().isAfter(time)) && (date.equals(LocalDate.now()))){
						System.out.println("Please enter a valid time!");
					} else {
						break;
					}
				}catch (DateTimeParseException e){
					System.out.println("Invalid time format");
				}
			}

			//create reservation
			rl.addReservation(name,pax, phoneNum, date, time, mem, curTableID);

			sm.reserveATable(curTableID);
		}

		// else say sorry, no reservation obj created
		else{
			System.out.println("No tables available for pax size. Please try again tomorrow.");
		}
	}

	static Boolean hasReservation(long rID) {
		return rl.getReservation(rID) != null;
	}

	static void removeReservation(long rID) {
		Reservation tempReservation = rl.getReservation(rID);
		rl.removeReservation(rID);
		sm.unassignTable(tempReservation.gettID());
	}

	//do we need this method?
	static int getAvailableTable(int pax) {
		return sm.getAvailTable(pax);
	}


	//order

	static void createOrder(Staff staff) {
		// check if there's reservation, ie has valid rID, yes-> create order, else deny
		long rID;

		//ask for rID
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter reservation ID: ");
		while (true){
			try {
				rID = Long.parseLong(scan.next());
				break; 
			} catch (NumberFormatException e){
				System.out.println("Error! Input is not a valid! Please enter a valid input!");
			}
		}


		if(hasReservation(rID)){
			Reservation reservation = rl.getReservation(rID);
			int table = sm.getAvailTable(reservation.getPax());
			orders.add(new Order(rID, staff, table, reservation.getMembership()));//is int or long? rID is long but orderID is int
			sm.assignTable(table);
			reservation.settID(table);
			System.out.println("Order created, table no. "+table+" assigned");
		} else {
			System.out.println("Your reservation has either expired or does not exist!");
		}
		
	}

	static void viewOrder(int orderID) {
		for(Order oItem : orders){
			if(oItem.getOrderID() == orderID){ //found order item
				oItem.printOrder();
				return;
			}
		}
		System.out.println("Invalid OrderID");
	}

	static void addItemToOrder(Order order) {
		HashMap<String, ArrayList<MenuItem>> tempHash = menu.getHashMenu();
		ArrayList<Promotional> tempPromo = menu.getPromoArrayList();
		boolean stillAdding = true;
		int userInput, numitems;

		while (stillAdding){
			System.out.println("1. Mains");
			System.out.println("2. Drinks");
			System.out.println("3. Desserts");
			System.out.println("4. Promotions");
			System.out.println("5. Exit");

			switch (getInput(1,5)){
				case 1:
					if (menu.getNumMains() == 0){
						System.out.println("No items under this category!");
						break;
					}
					menu.getMains();
					System.out.println("Which item do you want to add?");
					userInput = getInput(1,menu.getNumMains(),"Error! MainCourse does not exist. Please try again");
					System.out.println("Enter quantity");
					numitems = getInput(1,Integer.MAX_VALUE);
					order.addItemToOrder(tempHash.get("MainCourse").get(userInput-1), numitems);
					System.out.println("MainCourse added to order!");
					break;
				case 2:
					if (menu.getNumDrinks() == 0){
						System.out.println("No items under this category!");
						break;
					}
					menu.getDrinks();
					System.out.println("Which item do you want to add?");
					userInput = getInput(1,menu.getNumDrinks(),"Error! Drink does not exist. Please try again");
					System.out.println("Enter quantity");
					numitems = getInput(1,Integer.MAX_VALUE);
					order.addItemToOrder(tempHash.get("Drink").get(userInput-1), numitems);
					System.out.println("Drink added to order!");
					break;
				case 3:
					if (menu.getNumDessert() == 0){
						System.out.println("No items under this category!");
						break;
					}
					menu.getDesserts();
					System.out.println("Which item do you want to add?");
					userInput = getInput(1,menu.getNumDessert(),"Error! Dessert does not exist. Please try again");
					System.out.println("Enter quantity");
					numitems = getInput(1,Integer.MAX_VALUE);
					order.addItemToOrder(tempHash.get("Dessert").get(userInput-1), numitems);
					System.out.println("Dessert added to order!");
					break;
				case 4:
					if (menu.getNumPromotion() == 0){
						System.out.println("No items under this category!");
						break;
					}
					menu.getPromotions();
					System.out.println("Which item do you want to add?");
					userInput = getInput(1, tempPromo.size(),"Error! Promotion does not exist. Please try again");
					System.out.println("Enter quantity");
					numitems = getInput(1,Integer.MAX_VALUE);
					order.addItemToOrder(tempPromo.get(userInput-1), numitems);
					System.out.println("Item added to order!");
					break;
				case 5: stillAdding = false; break;
			}
		}
	}

	static void removeItemFromOrder(Order order) {
		HashMap<String, ArrayList<MenuItem>> tempHash = menu.getHashMenu();
		ArrayList<Promotional> tempPromo = menu.getPromoArrayList();
		boolean stillRemoving = true;
		int userInput;

		if (order.getOrderSize() == 0){
			System.out.println("Order is empty!");
			return;
		}

		while (stillRemoving){
			System.out.println("1. Mains");
			System.out.println("2. Drinks");
			System.out.println("3. Desserts");
			System.out.println("4. Promotions");
			System.out.println("5. Exit");
			switch (getInput(1,5)){
				case 1:
					if (menu.getNumMains() == 0){
						System.out.println("No items under this category!");
						break;
					}
					menu.getMains();
					System.out.println("Which item do you want to remove?");
					userInput = getInput(1,menu.getNumMains(),"Error! MainCourse does not exist. Please try again");
					System.out.println(order.removeItemFromOrder(tempHash.get("MainCourse").get(userInput-1)) ?
							"MainCourse removed from order!" :
							"This item cannot be removed since it doesn't exist in your order.");
					break;
				case 2:
					if (menu.getNumDrinks() == 0){
						System.out.println("No items under this category!");
						break;
					}
					menu.getDrinks();
					System.out.println("Which item do you want to remove?");
					userInput = getInput(1,menu.getNumDrinks(),"Error! Drink does not exist. Please try again");
					System.out.println(order.removeItemFromOrder(tempHash.get("Drink").get(userInput-1)) ?
							"Drink removed from order!" :
							"This item cannot be removed since it doesn't exist in your order.");
					break;
				case 3:
					if (menu.getNumDessert() == 0){
						System.out.println("No items under this category!");
						break;
					}
					menu.getDesserts();
					System.out.println("Which item do you want to remove?");
					userInput = getInput(1,menu.getNumDessert(),"Error! Dessert does not exist. Please try again");
					System.out.println(order.removeItemFromOrder(tempHash.get("Dessert").get(userInput-1)) ?
							"Dessert removed from order!" :
							"This item cannot be removed since it doesn't exist in your order.");
					break;
				case 4:
					if (menu.getNumPromotion() == 0){
						System.out.println("No items under this category!");
						break;
					}
					menu.getPromotions();
					System.out.println("Which item do you want to remove?");
					userInput = getInput(1,tempPromo.size(),"Error! Promotional item does not exist. Please try again");
					System.out.println(order.removeItemFromOrder(tempPromo.get(userInput-1)) ?
							"Promotional item removed from order!" :
							"This item cannot be removed since it doesn't exist in your order.");
					break;
				case 5: stillRemoving = false; break;
			}
		}
	}

	
	//check out

	static void printOrderInvoice(Order order) {
		CheckOut.doCheckOut(order, rl, sm);
	}

	//wrapping up

	static void printSaleRevenue() {
		double total=0;
		System.out.println("Sales Revenue Report for " + LocalDate.now());
		System.out.println("---------------------------------------------------------------");

		for (OrderItem tempItem : salesTracker.keySet()){
			System.out.println(tempItem.getOrderName() + "\t\t"+ salesTracker.get(tempItem));
		}

		for (Order tempOrder : orders){
			total += tempOrder.totalPrice();
		}
		System.out.println("---------------------------------------------------------------");
		System.out.println("Total revenue: " + NumberFormat.getCurrencyInstance().format(total));		
	}

	static void addToDailySales(OrderItem curItem){
		if (!(salesTracker.containsKey(curItem))){
			salesTracker.put(curItem, curItem.getOrderQuantity());
		} else {
			int existingNum = salesTracker.get(curItem);
			salesTracker.put(curItem, curItem.getOrderQuantity() + existingNum);
		}
	}
}
