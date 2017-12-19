package com.danklemme.trumpov;

/**
 * Created by Dan on 10/27/2017.
 */

public class TrumpovPair {

    private final String text;
    private final String type;

    public TrumpovPair(String text, String type) {
        this.text = text;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }
}
