package com.string.combiner.facade.combine.impl;

import com.string.combiner.facade.combine.CombineFacade;
import com.string.combiner.service.combine.CombineService;
import com.string.combiner.service.combine.impl.CombineServiceImpl;
import com.string.combiner.util.DataMapperUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringCombineFacadeImpl implements CombineFacade {

    private final static StringCombineFacadeImpl INSTANCE = new StringCombineFacadeImpl();
    private final CombineService service = CombineServiceImpl.getInstance();

    public static StringCombineFacadeImpl getInstance() {
        return INSTANCE;
    }

    public List<List<String>> getCombinedData(List<List<String>> data) {
        List<List<String>> combinedLines = new ArrayList<>();
        Map<String, List<List<String>>> groupedLines = DataMapperUtil.convertToMapFrom(data);
        int size = data.get(0).size();

        clearEmptyLines(groupedLines);

        boolean isTrue = true;
        while (isTrue) {
            Set<String> keys = groupedLines.keySet();

            List<String> keysToMergeRows = service.getKeysForCombiningLines(keys, size);
            if (keysToMergeRows.isEmpty()) {
                isTrue = false;
            } else {
                Map<String, List<String>> linesToCombine = service.getLinesToCombine(groupedLines, keysToMergeRows);
                List<String> combinedLine = service.combineLines(linesToCombine);
                combinedLines.add(combinedLine);
            }
        }
        return combinedLines;
    }

    private void clearEmptyLines(Map<String, List<List<String>>> map) {
        map.remove("");
    }
}
