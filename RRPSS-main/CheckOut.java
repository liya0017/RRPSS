import java.time.LocalDate;
import java.time.LocalTime;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;

class CheckOut {

	static void doCheckOut(Order myOrder, ReservationList rl, SeatingManagement sm) {
		System.out.println("---------------------------------------------------------------");
		System.out.println("RESTAURANT NAME");
		System.out.println("---------------------------------------------------------------");
		System.out.println("Server: "+ myOrder.getStaff().getName()+"\t\t"+LocalDate.now());
		System.out.println("Table: "+myOrder.getTableID()+"\t\t\t\t"+LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
		myOrder.printOrder();
		for(OrderItem item : myOrder.getMyOrder()){
			RRPSS.addToDailySales(item);
		}
		if (myOrder.getMembership()){
			System.out.println("10% discount given for membership!");
		}
		double price = myOrder.totalPrice(),serviceCharge = price * 0.1,gst = (price+serviceCharge)*0.07;
		System.out.println("\tSubtotal:\t"+ NumberFormat.getCurrencyInstance().format(price));
		System.out.println("\tService Charge:\t"+ NumberFormat.getCurrencyInstance().format(serviceCharge));
		System.out.println("\tGST:\t\t"+ NumberFormat.getCurrencyInstance().format(gst));
		System.out.println("\tTotal:\t\t"+ NumberFormat.getCurrencyInstance().format(price+serviceCharge+gst));
		System.out.println("---------------------------------------------------------------");

		rl.removeReservation(myOrder.getOrderID());
		sm.unassignTable(myOrder.getTableID());
		System.out.println("Table " + myOrder.getTableID() + " is set to EMPTY.");
	}
}
