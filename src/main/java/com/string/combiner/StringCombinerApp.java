package com.string.combiner;

import com.string.combiner.facade.combine.CombineFacade;
import com.string.combiner.facade.combine.impl.StringCombineFacadeImpl;
import com.string.combiner.service.file.FileHandler;
import com.string.combiner.service.file.impl.JsonFileServiceImpl;

import java.util.List;

public class StringCombinerApp {

    public static void main(String[] args) {
        FileHandler fileHandler = JsonFileServiceImpl.getInstance();
        CombineFacade combineFacade = StringCombineFacadeImpl.getInstance();

        List<List<String>> dataFromInputFile = fileHandler.getDataFromInputFile();
        List<List<String>> combinedData = combineFacade.getCombinedData(dataFromInputFile);
        fileHandler.saveToFileFrom(combinedData);
    }
}
