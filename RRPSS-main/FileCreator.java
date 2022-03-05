import java.time.LocalDate;
import java.time.LocalTime;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;

public class FileCreator {

    private FileCreator(){
        //prevent instantiation
    }
    
    static void outputReport(ArrayList<Order> orders, HashMap<OrderItem, Integer> salesTracker){
        try {
            File myFile = new File("SalesReport.txt");
            if (myFile.createNewFile()){
                System.out.println("File created: " + myFile.getName());
            } else {
                System.out.println("Sales Report file already exists, and has been updated");
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }

        try {
            int total=0;
            FileWriter myWriter = new FileWriter("SalesReport.txt");
            myWriter.write("Sales Revenue Report for " + LocalDate.now());
            myWriter.write("\r\n");
            myWriter.write("---------------------------------------------------------------");
            myWriter.write("\r\n");
            for (OrderItem tempItem : salesTracker.keySet()){
                    myWriter.write(tempItem.getOrderName() + "\t\t"+ salesTracker.get(tempItem));
                    myWriter.write("\r\n");
            }

            for (Order tempOrder : orders){
                    total += tempOrder.totalPrice();
            }
            myWriter.write("---------------------------------------------------------------");
            myWriter.write("\r\n");
            myWriter.write("Total revenue: " + NumberFormat.getCurrencyInstance().format(total));
            myWriter.close();

        } catch (IOException e){
            System.out.println(e.getMessage());
        }   
    }
}
