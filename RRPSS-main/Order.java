import java.util.ArrayList;
import java.text.NumberFormat;

class Order {

	private long orderID;
	private Staff staff;
	private ArrayList<OrderItem> myOrder;
	private int tableID;
	private boolean isMember;

	Order(long orderID, Staff staff, int tableID, boolean isMember){
		this.myOrder = new ArrayList<OrderItem>();
		this.orderID = orderID;
		this.staff = staff;
		this.tableID = tableID;
		this.isMember = isMember;
	}

	long getOrderID() {
		return this.orderID;
	}

	Staff getStaff() {
		return this.staff;
	}

	ArrayList<OrderItem> getMyOrder() {
		return myOrder;
	}

	int getTableID() {
		return tableID;
	}

	int getOrderSize(){
		return myOrder.size();
	}

	
	void addItemToOrder(Item item, int quantity) {
		
		for(OrderItem oItem : myOrder){
			if(oItem.getOrderName().equals(item.getName())){ //if item already exists in myOrder
				oItem.addMore(quantity);
				return;
			}
		}
		myOrder.add(new OrderItem(item, quantity)); //add new item in myOrder
	}
	

	boolean removeItemFromOrder(Item item) {
		
		for(OrderItem oItem : myOrder){
			if(oItem.getOrderName().equals(item.getName())){ //found order item
				myOrder.remove(oItem); //delete item from myOrder arraylist
				return true;
			}
		}
		
		//else if item not found in myOrder
		return false;
	}

	void printOrder() {
		System.out.println("---------------------------------------------------------------");
		for(OrderItem oItem : myOrder)
			System.out.println(oItem.getOrderQuantity() +"\t"+ oItem.getOrderName() +"\t\t" + NumberFormat.getCurrencyInstance().format(oItem.price()));
		if(myOrder.isEmpty())
			System.out.println("No items in order");
		System.out.println("---------------------------------------------------------------");
	}

	double totalPrice() {
		double totalPrice = 0;
		for(OrderItem oItem : myOrder)
			totalPrice += oItem.price();
		
		if (this.isMember){
			totalPrice = totalPrice*0.9;
		}
		return totalPrice;
	}
	
	boolean getMembership(){
		return this.isMember;
	}
}
