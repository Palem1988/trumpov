package com.danklemme.trumpov;

public class PeriodEndMarkovChain implements MarkovChain {

    private final MarkovIterator iterator;
    private final MarkovChain markovChain;

    public PeriodEndMarkovChain(MarkovChain markovChain) {
        this.markovChain = markovChain;
        this.iterator = markovChain.getIterator();
    }

    @Override
    public String chain(String seed, int chainLength) {
        String chain = markovChain.chain(seed, chainLength);
        if (!chain.endsWith(". ")) {
            chain = finishChainWithPeriod(chain);
        }
        return chain;
    }

    @Override
    public MarkovIterator getIterator() {
        return this.iterator;
    }

    private String finishChainWithPeriod(String previousChain) {
        String key = getLastKey(previousChain);
        String newChain = previousChain;
        boolean endsWithPeriod = false;
        while(!endsWithPeriod) {
            key = iterator.next(key);
            newChain = addSeedToChain(key, newChain);
            if (newChain.endsWith(". ")) {
                endsWithPeriod = true;
            }
        }
        return newChain;
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


    private String getLastKey(String chain) {
        int numWordsInKey = iterator.next("").length();  // Gets random key so we know the chain order.
        String[] splitChain =  chain.split("\\s+");
        StringBuilder keyBuilder = new StringBuilder();
        for (int i=splitChain.length-1-numWordsInKey; i<splitChain.length-1; i++) {
            keyBuilder.append(splitChain[i]);
            keyBuilder.append(" ");
        }
        return keyBuilder.toString();
    }
}
