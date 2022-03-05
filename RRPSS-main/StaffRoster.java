import java.util.*;
import java.util.Scanner;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.InputStream;

class StaffRoster {
	private ArrayList<Staff> availStaff;
	
	StaffRoster(){
		this.availStaff = new ArrayList<>();
        bootup();
	}

void bootup(){
    StaffReader StaffList = new StaffReader("StaffList.txt");
    try (Scanner filescan = new Scanner(StaffList.getIS())) {
        ArrayList<String> fileLines = new ArrayList<>();
        
        while (filescan.hasNextLine()){
            String line = filescan.nextLine();
            if (line.equals("END")){
                break;
            }
            
            if (line.isEmpty() || !filescan.hasNextLine()){
                String staffName = fileLines.get(0);
                String staffGender = fileLines.get(1);
                String jobTitle = fileLines.get(2);
                int staffID = Integer.valueOf(fileLines.get(3)); 
                this.availStaff.add(new Staff(staffName, staffGender, jobTitle, staffID));
                fileLines.clear();
            } else {
                fileLines.add(line);
            }
        }
    } catch (Exception e){
        System.out.println("Error: " + e.getMessage());
    }
}

void showStaff(){
	System.out.println("*******************************************");
	for (int i = 0; i < availStaff.size(); i++) {
		Staff tempStaff = availStaff.get(i);
		System.out.println("Name: " + tempStaff.getName());
		System.out.println("Gender: " + tempStaff.getGender());
		System.out.println("Role: " + tempStaff.getJobTitle());
		System.out.println("Employee ID: " + tempStaff.getEmployeeID());
		System.out.println("*******************************************");
	}
}

Staff getStaff(int pickStaff) { 
    // Display All Staff Names 
    Staff chosenStaff = availStaff.get(pickStaff); 
    return chosenStaff; 
   }

int getRosterSize(){
    return this.availStaff.size();
}
}
