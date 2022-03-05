class Table {

	private Order order;
	private int tableID;
	private int numOfSeats;
	private Status status;

	//package access level constructor (input tID & seats)
	Table(int tID, int seats){
		tableID = tID;
		numOfSeats = seats;
		status = Status.EMPTY;
		order = null;
	}

	Order getOrder() {
		return this.order;
	}

	int getTableID() {
		return this.tableID;
	}

	int getNumOfSeats() {
		return this.numOfSeats;
	}

	Status getStatus() {
		return this.status;
	}

	void setOrder(Order o){
		order = o;
	}

	void setStatus(Status s){
		status = s;
	}

}
