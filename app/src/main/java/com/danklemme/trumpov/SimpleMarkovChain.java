package com.danklemme.trumpov;

import java.util.ArrayList;
import java.util.List;

public class SimpleMarkovChain implements MarkovChain {

    private final MarkovIterator iterator;

    public SimpleMarkovChain(MarkovIterator iterator) {
        this.iterator = iterator;
    }

    @Override
    public List<String> chain(String seed, int chainLength) {
        String nextSeed = seed;
        List<String> chain = initialChain(seed);
        for (int i=0; i<chainLength; i++) {
            nextSeed = iterator.next(nextSeed);
            chain.add(getLastWord(nextSeed));
        }
        return chain;
    }

    @Override
    public MarkovIterator getIterator() {
        return this.iterator;
    }

    private List<String> initialChain(String seed) {
        String[] splitSeed = seed.split("\\s+");
        List<String> initialChain = new ArrayList<>();
        for (String word: splitSeed) {
            initialChain.add(word.replaceAll("\\s+",""));
        }
        return initialChain;
    }

    private String getLastWord(String seed) {
        String[] splitSeed = seed.split("\\s+");
        return splitSeed[splitSeed.length  - 1];
    }
}
