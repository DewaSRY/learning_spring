package com.sdewa.BasicSpring.bootstrap;

import java.util.List;
import java.io.File;

public interface BeverageNameListService {

    List<BeverageNameListRecord> getBeverageNameList(File file);
    
}
