package com.danklemme.trumpov;

import java.util.List;

public class CapitalStartMarkovChain implements MarkovChain {

    private final MarkovIterator iterator;
    private final MarkovChain markovChain;

    public CapitalStartMarkovChain(MarkovChain markovChain) {
        this.markovChain = markovChain;
        this.iterator = markovChain.getIterator();
    }


    @Override
    public List<String> chain(String seed, int chainLength) {
        String capitalStartSeed = seed;
        if (!startsWithUpper(seed)) {
            capitalStartSeed = getCapitalSeed(seed);
        }
        return markovChain.chain(capitalStartSeed, chainLength);
    }

    @Override
    public MarkovIterator getIterator() {
        return this.iterator;
    }

    private String getCapitalSeed(String oldSeed) {
        String capitalSeed = oldSeed;
        while (!startsWithUpper(capitalSeed)) {
            capitalSeed = iterator.next(capitalSeed);
        }
        return capitalSeed;
    }

    private boolean startsWithUpper(String str) {
        if (str.length() == 1)
            return str.toUpperCase().equals(str);
        else if (str.length() > 1)
            return str.substring(0, 1).toUpperCase().equals(str.substring(0, 1));
        else
            return false;
    }
}
