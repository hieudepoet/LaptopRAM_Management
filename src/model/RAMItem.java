package model;

public class RAMItem implements Comparable<RAMItem>{
    private String code;
    private String type;
    private int bus;
    private String brand;
    private int quantity;
    private String production_month_year;
    private boolean active;

    // public RAMItem() {
        
    // }

    public RAMItem(String code, String type, int bus, String brand, int quantity, String production_month_year) {
        this.code = code;
        this.type = type;
        this.bus = bus;
        this.brand = brand;
        this.quantity = quantity;
        this.production_month_year = production_month_year;
        this.active = true;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
         this.code = code;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setType(String type, int numerical_order) {
        this.type = type;
        setCode("RAM" + type + "_" + Integer.toString(numerical_order));
    }
    public int getBus() {
        return bus;
    }
    public void setBus(int bus) {
        this.bus = bus;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getProduction_month_year() {
        return production_month_year;
    }
    public void setProduction_month_year(String production_month_year) {
        this.production_month_year = production_month_year;
    }
    public boolean getActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return code + ", " + type + ", " + bus + ", " + brand + ", "
                + quantity + ", " + production_month_year;
    }

    public String tableString() {
        return String.format("|%-15s|%-10s|%-10s|%-20s|%-10s|%-15s|", code, type, bus, brand, quantity, production_month_year);
    }

    @Override
    public int compareTo(RAMItem item) {
        int types = type.compareTo(item.getType());
        if (types != 0) 
            return types;
        
        if (bus != item.getBus())
            return bus - item.getBus();

        int brands = brand.compareTo(item.getBrand());
        if (brands != 0) {
            return brands;
        }

        return production_month_year.compareTo(item.getProduction_month_year());
    }
}
