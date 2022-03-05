import java.util.Scanner;

class Staff {

	private String name;
	private String gender;
	private String jobTitle;
	private int employeeID;
	
    Staff(String name, String gender, String jobTitle, int ID){
            this.name = name;
            this.gender = gender;
            this.jobTitle = jobTitle;
            this.employeeID = ID;
        }

	String getName() {
		return this.name;
	}

	String getGender() {
		return this.gender;
	}

	String getJobTitle() {
		return this.jobTitle;
	}
	
	int getEmployeeID() {
		return this.employeeID;
	}
}
