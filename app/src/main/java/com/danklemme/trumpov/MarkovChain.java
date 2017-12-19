package com.danklemme.trumpov;

import java.util.List;

public interface MarkovChain {
    List<String> chain(String seed, int chainLength);
    MarkovIterator getIterator();
}
