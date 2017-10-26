package com.danklemme.trumpov;

public class SimpleMarkovChain implements MarkovChain {

    private final MarkovIterator iterator;

    public SimpleMarkovChain(MarkovIterator iterator) {
        this.iterator = iterator;
    }

    @Override
    public String chain(String seed, int chainLength) {
        String nextSeed = seed;
        String chain = nextSeed;  // Start chain out with the seed.
        for (int i=0; i<chainLength; i++) {
            nextSeed = iterator.next(nextSeed);
            chain = addSeedToChain(nextSeed, chain);
        }
        return chain;
    }

    @Override
    public MarkovIterator getIterator() {
        return this.iterator;
    }


    private String addSeedToChain(String seed, String previousChain) {
        StringBuilder chain = new StringBuilder(previousChain);
        chain.append(getLastWord(seed));
        chain.append(" ");
        return chain.toString();
    }

    private String getLastWord(String seed) {
        String[] splitSeed = seed.split("\\s+");
        return splitSeed[splitSeed.length  - 1];
    }
}
