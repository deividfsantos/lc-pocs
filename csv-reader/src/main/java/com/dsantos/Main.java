package com.dsantos;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, CsvValidationException {

        CSVReader reader = new CSVReader(new FileReader("emps.csv"));

        List<Employee> emps = new ArrayList<>();

        String[] record;

        while ((record = reader.readNext()) != null) {
            Employee emp = new Employee();
            emp.setId(record[0]);
            emp.setName(record[1]);
            emp.setAge(record[2]);
            emp.setCountry(record[3]);
            emps.add(emp);
        }

        System.out.println(emps);

        reader.close();
    }
}