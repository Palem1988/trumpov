package com.danklemme.trumpov;

import java.util.HashMap;
import java.util.List;

public interface GeneratableMarkov {

    /**
     * Generates a markov buildChain hashmap with the states being strings with order words in them
     * @param order Number of words a state is meant to be. order=1 means 1 word maps to 1 word, order=2  means 2 words map to 1 word.
     * @return Hashmap that maps strings
     */
    HashMap<String, List<String>> generate(int order);
}
