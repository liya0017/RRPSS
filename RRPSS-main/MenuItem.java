class MenuItem implements Item{
    private String name;
    private String description;
    private double price;
    
    MenuItem(String name, String description, double price){
        this.name = name;
        this.description = description;
        this.price = price;
    }
    
    void setName(String name){
        this.name = name;
    }
    
    void setDescription(String description){
        this.description = description;
    }
    
    void setPrice(Double price){
        this.price = price;
    }
    
    public String getName(){
        return this.name;
    }
    
    String getDescription(){
        return this.description;
    }
    
    public double getPrice(){
        return this.price;
    }
}
