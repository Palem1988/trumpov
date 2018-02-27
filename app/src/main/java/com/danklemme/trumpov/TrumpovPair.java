package com.danklemme.trumpov;

/**
 * Created by Dan on 10/27/2017.
 * Class to encapsulate how the text comes out. It has two data fields, text and type. The text
 * is simply the content of the markov-generated string, while the type says if it's a campaign
 * speech, tweet, etc.
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
