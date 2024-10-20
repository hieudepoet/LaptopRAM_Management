package DAO_RAM;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import model.RAMItem;
import util.RAMFileUtil;
import validator.Validator;

public class RAMManageSystem extends ArrayList<RAMItem> implements RAM_DAO{
    private RAMFileUtil rfu = new RAMFileUtil();
    private Scanner sc = new Scanner(System.in);
    private Map<String, Integer> types = new HashMap<>();

    public RAMManageSystem() {
        super();

        List<RAMItem> ramsFromFile = getAll();
        if (!ramsFromFile.isEmpty()) {
            this.addAll(ramsFromFile);
            reSort();
        }

        for (RAMItem ram : this) {
            if(types.containsKey(ram.getType())) {
                types.put(ram.getType(), types.get(ram.getType()) + 1);
            }
            else types.put(ram.getType(), 1);
        }
    }

    public void add(String type, int bus, String brand, int quantity, String production_month_year) {
        String code = genCode(type);
        RAMItem item = new RAMItem(code, type, bus, brand, quantity, production_month_year);
        this.add(item);
        reSort();
    }

    @Override
    public void add() {
        RAMItem item = itemForAdd();
        
        if (item != null) {
            this.add(item);
            reSort();
            System.out.println("Success!");

            System.out.print("Store to file? (Y/N): ");
            if(sc.nextLine().trim().equalsIgnoreCase("Y")) {
                saveToFile();
            }
        }
        else {
            System.out.println("Fail! Somthing goes wrong.");
        }
    }

    @Override
    public void search() {  
        List<RAMItem> rams = new ArrayList<>();
        String horizontalLine;
        String headerFormat = "| %-27s |\n";
        boolean running = true;
        boolean status;
        while(running) {
            rams.clear();
            status = true;
            horizontalLine = "+-----------------------------+";

            System.out.println();
            System.out.println(horizontalLine);
            System.out.printf(headerFormat, "    SEARCH TOOL");
            System.out.println(horizontalLine);
            System.out.printf(headerFormat, "1. Search by type");
            System.out.println(horizontalLine);
            System.out.printf(headerFormat, "2. Search by bus");
            System.out.println(horizontalLine);
            System.out.printf(headerFormat, "3. Search by brand");
            System.out.println(horizontalLine);
            System.out.printf(headerFormat, "4. Go back");
            System.out.println(horizontalLine);

            System.out.println();
            System.out.print("Your option: ");
            int option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1 : 
                    System.out.println();
                    System.out.print("Enter type: ");
                    rams = searchByType(sc.nextLine().trim());
                    
                    break;
                case 2 :
                    try {
                        System.out.println();
                        System.out.print("Enter bus: ");
                        String bus_s = sc.nextLine();
                        int bus = Integer.parseInt(bus_s);
                        rams = searchByBus(bus);
                    } 
                    catch (NumberFormatException e) {
                        System.out.println("Invalid number.");
                        status = false;
                    }

                    break;
                case 3 :
                    System.out.println();
                    System.out.print("Enter brand: ");
                    rams = searchByBrand(sc.nextLine().trim());
                    break;
                case 4 : 
                    running = false;
                    status = false;
                    return;
                default : 
                    System.out.println("Invalid option.");
                    continue;

            }

            if (rams.isEmpty() && status) {
                System.out.println("Not found.");
                continue;
            }

            horizontalLine = "+---------------+----------+----------+--------------------+----------+---------------+";
        
            System.out.println();
            System.out.println(horizontalLine);
            System.out.printf("|%-15s|%-10s|%-10s|%-20s|%-10s|%-15s|\n", "Code", "Type", "Bus", "Brand", "Quantity", "Production Year");
            System.out.println(horizontalLine);

            for (RAMItem ram : rams) {
                if (ram.getActive()) 
                    System.out.println(ram.tableString());
            }
            System.out.println(horizontalLine);
        }
    }

    @Override
    public void update() {
        System.out.print("Enter code: ");
        String code = sc.nextLine().trim();
        if (code == null || code.isEmpty()) {
            System.out.println("Invalid code!");
            return;
        }

        methodForUpdate(code);
    }

    @Override
    public void delete() {
        System.out.print("Enter code: ");
        String code = sc.nextLine().trim();
        if (code == null || code.isEmpty()) {
            System.out.println("Invalid code!");
            return;
        }

        for (RAMItem ram : this) {
            if (ram.getCode().equalsIgnoreCase(code) && ram.getActive()) {
                System.out.print("Delete the item? (Y/N): ");
                if (sc.nextLine().trim().equalsIgnoreCase("Y")) {
                    ram.setActive(false);
                    System.out.println("Success!");

                    System.out.print("Store to file? (Y/N): ");
                    if(sc.nextLine().trim().equalsIgnoreCase("Y")) {
                        saveToFile();
                    }

                    return;
                }
            }
        }

        System.out.println("Not found!");
    }

    @Override
    public void saveToFile(){
        rfu.writeFile("D:\\Code\\Java_Vscode\\LaptopRAM_Management\\src\\data\\RAMModules.dat", this);
        System.out.println("Saved!");
    }

    @Override
    public List<RAMItem> getAll() {
        return rfu.readFile("D:\\Code\\Java_Vscode\\LaptopRAM_Management\\src\\data\\RAMModules.dat");
    }

    public void clearFile(){
        rfu.clearFile("D:\\Code\\Java_Vscode\\LaptopRAM_Management\\src\\data\\RAMModules.dat");
        System.out.println("Cleared!");
    }
//==================================================Sub method===================================================
//===============================================================================================================
//===============================================================================================================
//===============================================================================================================

    private String genCode(String type) {
        int numerical_order;
        if (types.containsKey(type)) {
            types.put(type, types.get(type) + 1);
            numerical_order = types.get(type);
        }
        else {
            types.put(type, 1);
            numerical_order = 1;
        }
        String code = "RAM" + type + "_" + Integer.toString(numerical_order); 
        return code;
    }

    private RAMItem itemForAdd() {
        //Enter type
        System.out.print("Enter type : "); 
        String type = sc.nextLine();
        while(!Validator.isTypeValid(type)) {
            System.out.print("Invalid format! Enter again : ");
            type = sc.nextLine();
        }

        //Create code
        String code = genCode(type);

        //Enter bus
        System.out.print("Enter bus : ");
        String bus = sc.nextLine(); 
        while(!Validator.isBusValid(bus)) {
            System.out.print("Invalid number! Enter again : ");
            bus = sc.nextLine();
        }
        int busNumFormat = Integer.parseInt(bus);

        //Enter brand
        System.out.print("Enter brand : ");
        String brand = sc.nextLine();
        while(!Validator.isBrandValid(brand)) {
            System.out.print("Invalid name! Enter again : ");
            brand = sc.nextLine();
        }

        //Enter quantity
        System.out.print("Enter quantity : ");
        String quantity = sc.nextLine(); 
        while(!Validator.isQuantityValid(quantity)) {
            System.out.print("Invalid number! Enter again : ");
            quantity = sc.nextLine();
        }
        int quantityNumFormat = Integer.parseInt(quantity);

        //Enter production_month_year;
        System.out.print("Enter production_year_month (YYYY-MM): ");
        String production_year = sc.nextLine();
        while(!Validator.isTimeValid(production_year)) {
            System.out.print("Enter again : ");
            production_year = sc.nextLine();
        }

        return new RAMItem(code, type, busNumFormat, brand, quantityNumFormat, production_year);
    }


    private List<RAMItem> searchByType(String type) {  
        return this.stream()
                .filter(ram -> ram.getType().toLowerCase().contains(type.toLowerCase()))
                .collect(Collectors.toList());
    }

    private List<RAMItem> searchByBus(int bus) {
        return this.stream()        
                .filter(ram -> ram.getBus() == bus)
                .collect(Collectors.toList());
    }

    private List<RAMItem> searchByBrand(String brand) {  
        return this.stream()
                .filter(ram -> ram.getBrand().toLowerCase().contains(brand.toLowerCase()))
                .collect(Collectors.toList());
    }

    private void methodForUpdate(String code) {
        for(RAMItem ram : this) {
            if (ram.getCode().equals(code)) {
                System.out.println(ram.toString());
                System.out.println();

                //Update type
                System.out.print("Enter type: ");
                String type = sc.nextLine().trim();

                if (type != null && !type.isEmpty()) {
                    while (!Validator.isTypeValid(type)) {
                        System.out.print("Invalid format! Enter again: ");
                        type = sc.nextLine().trim();

                        if (type == null || type.isEmpty()) {
                            type = ram.getType();
                            break;
                        }
                    }
                    int numerical_order = 0;
                    if(type.equals(ram.getType())) {
                        String temp_code[] = ram.getCode().split("_");
                        numerical_order = Integer.parseInt(temp_code[1]);
                        ram.setType(type, numerical_order);
                    }   
                    else {
                        ram.setType(type);
                        ram.setCode(genCode(type));

                    }

                }

                //Update bus
                System.out.print("Enter bus : ");
                String bus = sc.nextLine().trim(); 

                if (bus != null && !bus.isEmpty()) {
                    while(!Validator.isBusValid(bus)) {
                        System.out.print("Invalid number! Enter again : ");
                        bus = sc.nextLine().trim();

                        if (bus == null || bus.isEmpty()) {
                            bus = Integer.toString(ram.getBus());
                            break;
                        }
                    }
                    ram.setBus(Integer.parseInt(bus));
                }

                //Update brand
                System.out.print("Enter brand: ");
                String brand = sc.nextLine().trim();

                if (brand != null && !brand.isEmpty()) {
                    while (!Validator.isBrandValid(brand)) {
                        System.out.print("Invalid name! Enter again: ");
                        brand = sc.nextLine().trim();

                        if (brand == null || brand.isEmpty()) {
                            brand = ram.getBrand();
                            break;
                        }
                    }
                    ram.setBrand(brand);
                }

                //Update quantity
                System.out.print("Enter quantity : ");
                String quantity = sc.nextLine().trim(); 

                if (quantity != null && !quantity.isEmpty()) {
                    while(!Validator.isQuantityValid(quantity)) {
                        System.out.print("Invalid number! Enter again : ");
                        quantity = sc.nextLine().trim();

                        if (quantity == null || quantity.isEmpty()) {
                            quantity = Integer.toString(ram.getQuantity());
                            break;
                        }
                    }
                    ram.setQuantity(Integer.parseInt(quantity));
                }

                System.out.println("Success!");
                reSort();

                System.out.print("Store to file? (Y/N): ");
                if(sc.nextLine().trim().equalsIgnoreCase("Y")) {
                    saveToFile();
                }
                return;
            }
        }

        System.out.println("Not found.");
    }

    private void reSort() {
        Collections.sort(this);
    }
}
