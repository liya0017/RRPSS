import java.util.ArrayList;
import java.math.*;

class Promotional implements Item{
    
	private int discount;  // discount = 30 means 30% off
    private String name;
    private ArrayList<MenuItem> items;
    private double totalprice;
    
    Promotional(String name){
    	this.discount = 20;
        this.name = name;
        this.totalprice = 0;
        this.items = new ArrayList<>();
    }
    
    void addItem(MenuItem item){
        this.items.add(item);
        updatePrice();
    }
    
    void removeItem(int num){
        this.items.remove(num);
        updatePrice();
    }
    
    void printItems(){
        System.out.println("Items included in the promotional set,");
        int i = 1;
        for (MenuItem temp: this.items){
            System.out.println(i + ". " + temp.getName());
            System.out.println("Description: " + temp.getDescription());
            System.out.println();
            i+=1;
        }
    }
    
    public double getPrice(){
        return this.totalprice;
    }
    
    public String getName(){
        return this.name;
    }
    
    int getNumOfItems(){
        return this.items.size();
    }
    
    void updatePrice(){
        double counter = 0;
        for (MenuItem temp: this.items){
            counter+= temp.getPrice();
        }
        this.totalprice = counter/100 * (100 - discount);
        this.totalprice = Math.round(this.totalprice * 100)/100;
    }
}
