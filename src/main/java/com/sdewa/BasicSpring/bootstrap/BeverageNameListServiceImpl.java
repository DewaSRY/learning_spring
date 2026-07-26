package com.sdewa.BasicSpring.bootstrap;


import java.util.List;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;


@Service
public class BeverageNameListServiceImpl implements BeverageNameListService {

    @Override
    public List<BeverageNameListRecord> getBeverageNameList(File file) {
        try {
            // Create a CsvToBeanBuilder to read the CSV file and map it to BeverageNameListRecord objects
            CsvToBean<BeverageNameListRecord> csvToBean = new CsvToBeanBuilder<BeverageNameListRecord>(new FileReader(file))
                    .withType(BeverageNameListRecord.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            // Parse the CSV file and return the list of BeverageNameListRecord objects
            return csvToBean.parse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
