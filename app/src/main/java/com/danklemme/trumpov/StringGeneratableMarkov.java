package com.danklemme.trumpov;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class StringGeneratableMarkov implements GeneratableMarkov {

    private final ArrayList<String> markovTexts;

    public StringGeneratableMarkov() {
        markovTexts = new ArrayList<>();
    }

    public StringGeneratableMarkov(String markovString) {
        markovTexts = new ArrayList<>();
        markovTexts.add(markovString);
    }

    private StringGeneratableMarkov(ArrayList<String> texts) {
        markovTexts = texts;
    }

    public StringGeneratableMarkov addString(String newString) {
        ArrayList<String> newArray = new ArrayList<>(markovTexts);
        newArray.add(newString);
        return new StringGeneratableMarkov(newArray);
    }

    public ArrayList<String> getMarkovTexts() {
        return markovTexts;
    }

    @Override
    public HashMap<String, List<String>> generate(int stepSize) {
        HashMap<String, List<String>> markovMap = new HashMap<>();
        for (String markovText: markovTexts) {
            String[] splitText = markovText.split("\\s+");
            HashMap<String, List<String>> newMarkov = putTextInHashMap(splitText, stepSize);
            for (HashMap.Entry<String, List<String>> entry : newMarkov.entrySet())
            {
                if(!markovMap.containsKey(entry.getKey())) {
                    markovMap.put(entry.getKey(), entry.getValue());
                }
                else {
                    markovMap.get(entry.getKey()).addAll(entry.getValue());
                }
            }
        }
        return markovMap;
    }

    private HashMap<String, List<String>> putTextInHashMap(String[] splitText, int stepSize) {
        HashMap<String, List<String>> markovMap = new HashMap<>();
        for (int valueIndex=stepSize; valueIndex<splitText.length; valueIndex++) {  // Start at stepSize because key must be that long.
            String value = splitText[valueIndex];
            StringBuilder keyBuilder = new StringBuilder();
            for (int keyIndex=stepSize; keyIndex>0; keyIndex--) {
                keyBuilder.append(splitText[valueIndex-keyIndex]);
                keyBuilder.append(" ");
            }
            String key = keyBuilder.toString();
            if (!markovMap.containsKey(key)) {
                markovMap.put(key, new ArrayList<String>());
            }
            markovMap.get(key).add(value);
        }
        return markovMap;
    }
}
