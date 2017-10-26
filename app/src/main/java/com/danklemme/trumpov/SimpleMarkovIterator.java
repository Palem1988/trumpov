package com.danklemme.trumpov;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SimpleMarkovIterator implements MarkovIterator {

    private final HashMap<String, List<String>> markovMap;

    public SimpleMarkovIterator(GeneratableMarkov markov, int order) {
        this.markovMap = markov.generate(order);
    }

    public SimpleMarkovIterator(HashMap<String, List<String>> markovMap) {
        this.markovMap = markovMap;
    }

    @Override
    public String next(String seed) {
        String newSeed;
        if (!markovMap.containsKey(seed)) {
            newSeed = randomSeed();
        }
        else {
            newSeed = buildNextSeed(seed);
        }
        return newSeed;
    }

    private String randomSeed() {
        List<String> keysAsArray = new ArrayList<>(markovMap.keySet());
        Random r = new Random();
        return keysAsArray.get(r.nextInt(keysAsArray.size()));
    }


    private String buildNextSeed(String oldSeed) {
        String newEnd = getRandom(markovMap.get(oldSeed));
        String[] oldArray = oldSeed.split("\\s+");
        StringBuilder newSeedBuilder = new StringBuilder();
        for (int i=0; i<oldArray.length; i++) {
            if (i < oldArray.length -1) {
                newSeedBuilder.append(oldArray[i+1]);
            }
            else {
                newSeedBuilder.append(newEnd);
            }
            newSeedBuilder.append(" ");
        }
        return newSeedBuilder.toString();
    }


    private String getRandom(List<String> list) {
        return list.get(new Random().nextInt(list.size()));
    }
}
