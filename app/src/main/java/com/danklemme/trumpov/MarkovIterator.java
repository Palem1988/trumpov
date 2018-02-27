package com.danklemme.trumpov;

/**
 * Created by Dan on 10/25/2017.
 *
 * Interface to encapsulate the Markovian process of getting the next value in the Markov Chain.
 */

public interface MarkovIterator {

    /**
     * Get the next String in the Markov Process.
     * @param seed String that we are basing the current Markov Process off of.
     * @return Next string in the sequence.
     */
    String next(String seed);
}
