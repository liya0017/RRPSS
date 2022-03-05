import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

class ReservationList {
	private static final long REMOVE_AFTER_SECONDS = 60;
	
	private final ArrayList<Reservation> resList;
	private static long NEXT_RID;
	
	ReservationList(){
		resList = new ArrayList <Reservation>();
		NEXT_RID = 1;
	}

	ArrayList<Reservation> getResList() {
		return resList;
	}

	void addReservation(String name, int pax, long phoneNum, LocalDate resDate, LocalTime resTime, Boolean mem, int tID) {
		Reservation tempReservation = new Reservation(name, pax, phoneNum, resDate, resTime, mem, NEXT_RID, tID);
		resList.add(tempReservation);

		System.out.println("Please note, reservation ID assigned is : " + NEXT_RID);
		NEXT_RID++;	
	}

	void removeReservation(long rID) {
		if (resList.removeIf(res -> (res.getrID() == rID))){
			System.out.println("Reservation, reservationID =  " + rID+ ", is successfully removed");
		} else
			System.out.println("Error in removing reservation. No such reservation");
	}

	Reservation getReservation(long rID){
		for (Reservation res : resList){
			if (res.getrID() == rID)
				return res;
		}
		return null;
	}

	void removeInvalids(SeatingManagement sm){
		ArrayList <Long> toRemove = new ArrayList<Long>();
		int i=0;
		for (Reservation tempReservation : resList){
			if (tempReservation.getReservationDate().isBefore(LocalDate.now())){
				toRemove.add(tempReservation.getrID());
			} 
			if ((LocalTime.now().isAfter(tempReservation.getReservationTime().plusSeconds(REMOVE_AFTER_SECONDS))) && (tempReservation.getReservationDate().equals(LocalDate.now()))){
				toRemove.add(tempReservation.getrID());
			}
		}
		while (toRemove.size() != 0){
			sm.unassignTable(getReservation(toRemove.get(0)).gettID());
			resList.remove(getReservation(toRemove.get(0)));
			toRemove.remove(0);
		}
	}
}
