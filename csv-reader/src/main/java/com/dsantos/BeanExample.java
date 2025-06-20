package com.dsantos;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class BeanExample {
    public static void main(String[] args) throws IOException {

        CSVReader reader = new CSVReader(new FileReader("emps.csv"));

        ColumnPositionMappingStrategy<Employee> beanStrategy = new ColumnPositionMappingStrategy<Employee>();
        beanStrategy.setType(Employee.class);
        beanStrategy.setColumnMapping("id", "name", "age", "country");

        CsvToBean<Employee> csvToBean = new CsvToBean<>();

        csvToBean.setCsvReader(reader);
        csvToBean.setMappingStrategy(beanStrategy);
        List<Employee> emps = csvToBean.parse();

        System.out.println(emps);

    }
}
