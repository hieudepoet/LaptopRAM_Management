package service;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import DAO_RAM.RAMManageSystem;
import model.RAMItem;

public class RAMService {
    private RAMManageSystem ramManager = new RAMManageSystem();
    private Scanner sc = new Scanner(System.in);

    public RAMService() {}

    public void showMenu() {
        // ramManager.add("DDR4", 3200, "Corsair", 10, "2023-10");
        // ramManager.add("DDR4", 3600, "G.Skill", 5, "2023-09");
        // ramManager.add("DDR3", 1600, "Kingston", 15, "2022-08");
        // ramManager.add("DDR4", 2400, "Crucial", 20, "2023-07");
        // ramManager.add("DDR5", 4800, "Samsung", 8, "2024-01");
        // ramManager.add("LPDDR4", 2133, "Micron", 12, "2022-11");
        // ramManager.add("DDR3", 1333, "Patriot Memory", 6, "2021-05");
        // ramManager.add("DDR4", 2666, "ADATA", 25, "2023-02");
        // ramManager.add("LPDDR5", 6400, "Hynix", 3, "2024-03");
        // ramManager.add("DDR4", 3200, "Transcend", 7, "2023-12");
        // ramManager.saveToFile();
        
        //ramManager.clearFile();

        // ramManager.removeIf(ram -> ram.getType().equals("A3"));
        // ramManager.removeIf(ram -> ram.getType().equals("R3"));

        AtomicBoolean running = new AtomicBoolean(true);
        Scanner sc = new Scanner(System.in);

        while (running.get()) {
            printTableMenu();

            System.out.println();
            System.out.print("Your option: ");
            int choice = sc.nextInt();
            sc.nextLine();
            
            switch (choice) {
                case 1: //done
                    function1();
                    askToGoBackToTheMenu(sc, running);
                    break;
                case 2: //done
                    function2();
                    askToGoBackToTheMenu(sc, running);
                    break;
                case 3: //done
                    function3();
                    askToGoBackToTheMenu(sc, running);
                    break;
                case 4:
                    function4();
                    askToGoBackToTheMenu(sc, running);
                    break;
                case 5: //done
                    function5();
                    askToGoBackToTheMenu(sc, running);
                    break;
                case 6 :
                    function6(); //done
                    askToGoBackToTheMenu(sc, running);
                    break;
                case 7: //done
                    function7(running); //done
                    break;
                default:
                    System.out.println("Invalid option.");
            }

            System.out.println();
        }

        System.out.print("Save the changes? (Y/N): ");
            if (sc.nextLine().trim().equalsIgnoreCase("Y")) 
                ramManager.saveToFile();
        
        System.out.println();
        System.out.println("Program ends.");
    }

    public void askToGoBackToTheMenu(Scanner sc, AtomicBoolean running) {
        System.out.println();
        System.out.print("Go back to the main menu? (Y/N) : ");
        if (sc.nextLine().equalsIgnoreCase("N")) {
            running.set(false);
        }
    }

//==================================================Sub method===================================================
//===============================================================================================================
//===============================================================================================================
//===============================================================================================================
private void printTableMenu() {
    String horizontalLine = "+-----------------------------+";
    String headerFormat = "| %-27s |\n";

    System.out.println(horizontalLine);
    System.out.printf(headerFormat, "    RAM MANAGEMENT MENU");
    System.out.println(horizontalLine);
    System.out.printf(headerFormat, "1. Add RAM Item");
    System.out.println(horizontalLine);
    System.out.printf(headerFormat, "2. Search RAM Item");
    System.out.println(horizontalLine);
    System.out.printf(headerFormat, "3. Update RAM Item");
    System.out.println(horizontalLine);
    System.out.printf(headerFormat, "4. Delete RAM Item");
    System.out.println(horizontalLine);
    System.out.printf(headerFormat, "5. Show all RAM Items");
    System.out.println(horizontalLine);
    System.out.printf(headerFormat, "6. Store Data to File");
    System.out.println(horizontalLine);
    System.out.printf(headerFormat, "7. Exit");
    System.out.println(horizontalLine);
}

    private void function1() {
        while (true) {
            System.out.println();
            System.out.println("--------------ADD---------------");
            ramManager.add();

            System.out.print("Continue adding a module? (Y/N): ");
            if(sc.nextLine().trim().equals("N")) break;
        }
    }

    private void function2() {
        System.out.println();
        System.out.println("------------SEARCH--------------");
        ramManager.search();
    }

    private void function3() {
        System.out.println();
        System.out.println("------------UPDATE--------------");
        ramManager.update();
    }

    private void function4() {
        System.out.println("------------DELETE--------------");
        ramManager.delete();
    }

    private void function5() {
        String horizontalLine = "+---------------+----------+----------+--------------------+----------+---------------+";
        
        System.out.println();
        System.out.println(horizontalLine);
        System.out.printf("|%-15s|%-10s|%-10s|%-20s|%-10s|%-15s|\n", "Code", "Type", "Bus", "Brand", "Quantity", "Production Year");
        System.out.println(horizontalLine);

        for (RAMItem ram : ramManager) {
            if (ram.getActive()) 
                System.out.println(ram.tableString());
        }
        System.out.println(horizontalLine);
    }

    private void function6() {
        ramManager.saveToFile();
    } 

    private void function7(AtomicBoolean running) {
        System.out.println("Ending...");
        running.set(false);
    }
}
