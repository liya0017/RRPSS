import java.time.LocalDate;
import java.time.LocalTime;

class Reservation {

	private LocalDate reservedDate;
	private LocalTime reservedTime;
	private String custName;
	private int pax;
	private long contact;
	private Boolean membership;
	private long rID;
	private int tID;

	Reservation(String name, int pax, long phoneNum, LocalDate resDate, LocalTime resTime, Boolean mem, long rId, int tID){
		custName = name;
		this.pax = pax;
		contact = phoneNum;
		reservedDate = resDate;
		reservedTime = resTime;
		membership = mem;
		rID = rId;
		this.tID = tID;
	}

	LocalDate getReservationDate() {
		return reservedDate;
	}

	LocalTime getReservationTime() {
		return reservedTime;
	}

	int getPax() {
		return this.pax;
	}

	String getCustName() {
		return this.custName;
	}

	Boolean getMembership() {
		return this.membership;
	}

	long getrID(){
		return rID;
	}
	
	int gettID(){
		return tID;
	}

	void settID(int tID) {
		this.tID = tID;
	}
}
