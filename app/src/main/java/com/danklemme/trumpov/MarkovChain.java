package com.danklemme.trumpov;

public interface MarkovChain {
    String chain(String seed, int chainLength);
    MarkovIterator getIterator();
}
