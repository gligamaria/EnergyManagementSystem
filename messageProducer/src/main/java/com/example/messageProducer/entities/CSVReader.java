package com.example.messageProducer.entities;

import org.springframework.scheduling.annotation.Scheduled;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    private final List<String> stringValues;

    public CSVReader() {
        stringValues = new ArrayList<>();
        String csvFilePath = "C:\\Users\\user\\Downloads\\sensor.csv"; // Replace with your file path

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    float floatValue = Float.parseFloat(line.trim());
                    String stringValue = Float.toString(floatValue);
                    stringValues.add(stringValue);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid float value: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getStringValues() {
        return stringValues;
    }
}
