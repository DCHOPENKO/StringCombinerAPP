package com.string.combiner.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class DataMapperUtil {

    public static Map<String, List<List<String>>> convertToMapFrom(List<List<String>> source) {
        return source
                .stream()
                .collect(Collectors.toMap(arr -> {
                            StringBuilder sb = new StringBuilder();
                            arr.forEach(value -> {
                                if (!value.equals("null")) {
                                    sb.append(arr.indexOf(value));
                                }
                            });
                            return sb.toString();
                        },
                        it -> {
                            List<List<String>> lst = new ArrayList();
                            lst.add(it);
                            return lst;
                        },
                        (a, b) -> Stream.concat(a.stream(), b.stream()).collect(Collectors.toList()),
                        HashMap::new));
    }

}
