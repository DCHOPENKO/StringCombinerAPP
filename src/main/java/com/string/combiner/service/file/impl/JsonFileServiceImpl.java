package com.string.combiner.service.file.impl;

import com.string.combiner.service.file.FileHandler;
import com.string.combiner.util.JsonParserUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.json.JSONArray;

import java.nio.file.*;
import java.util.List;

import static java.nio.file.StandardOpenOption.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonFileServiceImpl implements FileHandler {

    private final static Path INPUT_PATH = Paths.get("input.json");
    private final static Path OUTPUT_PATH = Paths.get("output.json");
    private final static JsonFileServiceImpl INSTANCE = new JsonFileServiceImpl();

    public static JsonFileServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    @SneakyThrows
    public List<List<String>> getDataFromInputFile() {
        String rawData = Files.readString(INPUT_PATH);
        return JsonParserUtil.parseFrom(rawData);
    }

    @Override
    @SneakyThrows
    public Path saveToFileFrom(List<List<String>> data) {
        JSONArray jsonArray = new JSONArray(data);
        Files.writeString(OUTPUT_PATH, jsonArray.toString(), CREATE, TRUNCATE_EXISTING);
        return OUTPUT_PATH;
    }
}
