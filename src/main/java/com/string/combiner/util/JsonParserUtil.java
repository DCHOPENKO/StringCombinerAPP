package com.string.combiner.util;

import lombok.experimental.UtilityClass;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class JsonParserUtil {

    public static List<List<String>> parseFrom(String text) {
        List<List<String>> lines = new ArrayList<>();
        JSONArray commonJsonArray = new JSONArray(text);

        commonJsonArray.forEach(it -> {
            List<String> line = new ArrayList<>();
            JSONArray jsonArray = (JSONArray) it;

            jsonArray.forEach(jArray -> {
                line.add(String.valueOf(jArray));
            });

            lines.add(line);
        });
        return lines;
    }
}
