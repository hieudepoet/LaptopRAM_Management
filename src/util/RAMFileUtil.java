package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.RAMItem;

public class RAMFileUtil extends FileUtil<RAMItem>{

    @Override
    public List<RAMItem> readFile(String filePath) {
    List<RAMItem> items = new ArrayList<>();

    File file = new File(filePath);

    if (!file.exists()) {
        try {
            file.createNewFile();
        }
        catch(IOException e) {
            System.out.println("Cannot initilized file!");
            e.printStackTrace();
            return items;
        }
    }
            

    try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        int line_number = 0;
        String line;
        while ((line = reader.readLine()) != null) {
            line_number++;
            try {
                String[] data = line.split(",");

                    if (data.length == 7) {
                        RAMItem item = new RAMItem(
                            data[0].trim(),
                            data[1].trim(),
                            Integer.parseInt(data[2].trim()),
                            data[3].trim(),
                            Integer.parseInt(data[4].trim()),
                            data[5].trim()
                        );

                        item.setActive(data[6].trim().equalsIgnoreCase("active"));
                        items.add(item);
                    }
                    else {
                        System.out.println("Data format error: " + line_number);
                    }



                } catch(NumberFormatException e) {
                    System.out.println("Invalid data syntax: " + line_number);
                }
            }
            
        }
        catch(IOException e) {

            System.out.println("Error reading from file: " + filePath);
        }

        return items;
    }

    @Override
    public void writeFile(String filePath, List<RAMItem> items) {

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for(RAMItem item : items) {
                writer.write(item.toString() + ", " + (item.getActive() == true ? "active" : "inactive"));
                writer.newLine();
            }
        }
        catch (IOException e) {
            System.out.println("Error writing to file: " + filePath);
        }
    }

    @Override
    public void clearFile(String filePath) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

        }
        catch (IOException e) {
            System.out.println("Error clearing file: " + filePath);
        }
    }
}
