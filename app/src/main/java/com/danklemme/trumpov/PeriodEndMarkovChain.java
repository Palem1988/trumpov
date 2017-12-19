package com.danklemme.trumpov;

import java.util.ArrayList;
import java.util.List;

public class PeriodEndMarkovChain implements MarkovChain {

    private final MarkovIterator iterator;
    private final MarkovChain markovChain;

    public PeriodEndMarkovChain(MarkovChain markovChain) {
        this.markovChain = markovChain;
        this.iterator = markovChain.getIterator();
    }

    @Override
    public List<String> chain(String seed, int chainLength) {
        List<String> chain = markovChain.chain(seed, chainLength);
        if (!chain.get(chain.size() - 1).endsWith(".")) {
            chain = finishChainWithPeriod(chain);
        }
        return chain;
    }

    @Override
    public MarkovIterator getIterator() {
        return this.iterator;
    }

    private List<String> finishChainWithPeriod(List<String> previousChain) {
        String seed = getLastSeed(previousChain);
        List<String> newChain = new ArrayList<>(previousChain);
        boolean endsWithPeriod = false;
        while(!endsWithPeriod) {
            seed = iterator.next(seed);
            newChain = addSeedToChain(seed, newChain);
            if (newChain.get(newChain.size() - 1).endsWith(".")) {
                endsWithPeriod = true;
            }
        }
        return newChain;
    }

    private List<String> addSeedToChain(String seed, List<String> previousChain) {
        List<String> chain = new ArrayList<>(previousChain);
        chain.add(getLastWord(seed));
        return chain;
    }

    private String getLastWord(String seed) {
        String[] splitSeed = seed.split("\\s+");
        return splitSeed[splitSeed.length  - 1];
    }


    private String getLastSeed(List<String> chain) {
        // Gets random key so we know the chain order.
        int numWordsInKey = iterator.next("").split("\\s+").length;
        StringBuilder keyBuilder = new StringBuilder();
        for (int i=chain.size()-1-numWordsInKey; i<chain.size()-1; i++) {
            keyBuilder.append(chain.get(i));
            keyBuilder.append(" ");
        }
        return keyBuilder.toString();
    }
}
