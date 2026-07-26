package com.sdewa.BasicSpring.bootstrap;

import com.opencsv.bean.CsvBindByName;

import lombok.Data;

@Data
public class BeverageNameListRecord {
    @CsvBindByName(column = "Category")
    private String category;
    @CsvBindByName(column = "Beverage Name")
    private String beverageName;
    @CsvBindByName(column = "Type")
    private String type;
    @CsvBindByName(column = "Main Ingredient")
    private String mainIngredient;
}
