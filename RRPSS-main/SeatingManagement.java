/* Tables are created in this class with table ID ranging from 1 - 20
	paxPerTable ranges from 2-6
	4 Tables for each paxPerTable size

	Package level access
*/


import java.util.*;

class SeatingManagement {

	public static final int NUMBER_OF_TABLES = 20;

	private ArrayList <Table> myTables = new ArrayList <Table>();
	
	SeatingManagement (){
		//creation of tables + adding it to myTables arrayList
		
		int paxPerTable = 2;
		for (int i  = 0; i < NUMBER_OF_TABLES; i++){
			
			myTables.add(i, new Table(i+1, paxPerTable));

			if((i+1) % 4 == 0)
				paxPerTable+=2;
		}
	}

	int getAvailTable(int seatsNeeded) {

		for(Table t : myTables){
			if (t.getStatus() == Status.EMPTY && t.getNumOfSeats() >= seatsNeeded){
				return t.getTableID();
			}
		}
		return -1; //if no available table found
	}

	void reserveATable(int tID) {
		myTables.get(tID-1).setStatus(Status.RESERVED); //set status of table of tID from empty to reserved
	}

	void assignTable(int tID) {
		myTables.get(tID-1).setStatus(Status.ASSIGNED); //set status of table of tID from reserved to assigned
	}

	void unassignTable(int tID) {
		myTables.get(tID-1).setStatus(Status.EMPTY); //set status of table of tID from assigned/reserved to empty
	}

	Table getTable(int tID){
		return myTables.get(tID-1);
	}
	
	void printTableStatus(){
		for (Table tempTable : myTables){
			System.out.println("Table " + tempTable.getTableID() + " of size " + tempTable.getNumOfSeats() + ", Status: " + tempTable.getStatus());
		}
	}
}
