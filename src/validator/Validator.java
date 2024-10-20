package validator;

import java.time.YearMonth;

public class Validator {
    // public static boolean isCodeValid(String code, RAMManageSystem rams) {
    //     code = code.trim();
    //     boolean check = true;

    //     if (code.isEmpty() || code == null) {
    //         System.out.print("Invalid code! ");
    //         return false;
    //     }

    //     if (!code.matches("^RAM[A-Za-z]+[0-9]+_\\d+$")) {
    //         System.out.print("Invalid code! ");
    //         return false;
    //     }

    //     for (RAMItem ram : rams) {
    //         if(ram.getCode().equalsIgnoreCase(code))
    //             check = false;
    //     }

    //     if (!check) 
    //         System.out.print("Existed code! ");
    //     return check;
    // }

    public static boolean isTypeValid(String type) {
        type = type.trim();

        return (type != null && !type.isEmpty() && type.matches("^[A-Z]+[0-9]+$"));
    }

    public static boolean isBusValid(String bus) {
        bus = bus.trim();

        if (bus == null || bus.isEmpty()) {
            return false;    
        }

        int busNumFormat;
        try {
            busNumFormat = Integer.parseInt(bus);
        } catch(NumberFormatException e) {

            return false;
        }
        
        return busNumFormat >= 0;
    } 

    public static boolean isBrandValid(String brand) {
        brand = brand.trim();
        return brand != null && !brand.isEmpty();
    }

    public static boolean isQuantityValid(String quantity) {
        return isBusValid(quantity);
    } 

    public static boolean isTimeValid(String product_month_year) {
        product_month_year = product_month_year.trim();
        String[] time;
        
        time = product_month_year.split("-");
        int year;
        int month;

        try {
            year = Integer.parseInt(time[0].trim());
            month = Integer.parseInt(time[1].trim());
        } catch (NumberFormatException e) {
            System.out.print("Invalid time format! ");
            return false;
        }

        YearMonth now = YearMonth.now();

        if (((year >= 2000) 
            && (year <= now.getYear()) 
            && (month >= 1) 
            && (year == now.getYear() ? month <= now.getMonthValue() : month <= 12)) == false) 
                System.out.print("Invalid time! "); 

        return (year >= 2000) 
            && (year <= now.getYear()) 
            && (month >= 1) 
            && (year == now.getYear() ? month <= now.getMonthValue() : month <= 12);
    }
}
