package com.danklemme.trumpov;

import java.util.List;


/**
 * Interface for the Markov Chain. All classes that implement MarkovChain should have a
 * MarkovIterator to do the heavy lifting of generating the next word in the chain.
 */
public interface MarkovChain {
    /**
     * Generate a list of strings that are the words in the Markov Chain.
     * @param seed Initial word to seed the chain with.
     * @param chainLength Target length for the chain.
     * @return List of strings that are the words (in order) of the Markov Chain
     */
    List<String> chain(String seed, int chainLength);

    /**
     * Method to get the MarkovIterator that handles acquiring the next word in the chain.
     * @return The MarkovIterator used by this MarkovChain.
     */
    MarkovIterator getIterator();
}
