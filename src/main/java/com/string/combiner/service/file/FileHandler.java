package com.string.combiner.service.file;

import java.nio.file.Path;
import java.util.List;

public interface FileHandler {

    List<List<String>> getDataFromInputFile();

    Path saveToFileFrom(List<List<String>> data);

}
