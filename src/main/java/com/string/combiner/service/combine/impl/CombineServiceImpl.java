package com.string.combiner.service.combine.impl;

import com.string.combiner.service.combine.CombineService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CombineServiceImpl implements CombineService {

    private final static CombineServiceImpl INSTANCE = new CombineServiceImpl();

    public static CombineServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<String> getKeysForCombiningLines(Set<String> keys, int length) {
        ArrayList<String> keysCopy = new ArrayList<>(keys);
        AtomicBoolean lineIsFull = new AtomicBoolean(false);
        List<String> resultKeys = new ArrayList<>();

        keys.forEach(currentKey -> {

            IntStream.range(0, keys.size()).forEach((i) -> {

                if (!lineIsFull.get()) {

                    StringBuilder fullKey = new StringBuilder(currentKey);
                    resultKeys.clear();
                    resultKeys.add(currentKey);

                    keysCopy.forEach(loopKey -> {

                        if (!lineIsFull.get()) {
                            if (!isContains(fullKey.toString(), loopKey)) {
                                fullKey.append(loopKey);
                                resultKeys.add(loopKey);
                            }
                            if (fullKey.toString().length() == length) {
                                lineIsFull.set(true);
                            }
                        }
                    });
                    Collections.rotate(keysCopy, -1);
                }
            });
        });

        if (resultKeys.size() == 1 && resultKeys.get(0).length() < 4) {
            resultKeys.clear();
        }
        return resultKeys;
    }

    @Override
    public Map<String, List<String>> getLinesToCombine
            (Map<String, List<List<String>>> source, List<String> keys) {

        Map<String, List<String>> linesToMerge = new HashMap<>();
        List<String> keysToDelete = new ArrayList<>();

        source.entrySet()
                .stream()
                .filter(it -> keys.contains(it.getKey()))
                .forEach(it -> {
                    if (it.getValue().size() == 1) {
                        linesToMerge.put(it.getKey(), it.getValue().get(0));
                        keysToDelete.add(it.getKey());
                    } else {
                        List<String> value = it.getValue().remove(0);
                        linesToMerge.put(it.getKey(), value);
                        source.put(it.getKey(), it.getValue());
                    }
                });

        deleteUsedDataFromSource(source, keysToDelete);

        return linesToMerge;
    }

    @Override
    public List<String> combineLines(Map<String, List<String>> lines) {

        int size = lines.values().stream().findFirst().orElse(Collections.EMPTY_LIST).size();

        String[] result = new String[size];

        lines.forEach((key, value) -> {
            Arrays.stream(key.split("")).forEach(index -> {
                int i = Integer.parseInt(index);
                result[i] = value.get(i);
            });
        });
        return Arrays.asList(result);
    }

    private boolean isContains(String keyFirst, String keySecond) {
        return Arrays.stream(keySecond.split(""))
                .anyMatch(keyFirst::contains);

    }

    private void deleteUsedDataFromSource(Map<String, List<List<String>>> source, List<String> keysToDelete) {
        if (!keysToDelete.isEmpty()) {
            keysToDelete.forEach(source::remove);
        }
    }
}
