package com.string.combiner.service.combine;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CombineService {

    List<String> getKeysForCombiningLines(Set<String> keys, int length);

    Map<String, List<String>> getLinesToCombine
            (Map<String, List<List<String>>> source, List<String> keys);

    List<String> combineLines(Map<String, List<String>> lines);
}
