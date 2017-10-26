package com.danklemme.trumpov;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private MarkovChain chain;
    private final int numWords = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    private void generateMarkovText() {
        String text = chain.chain("", numWords);
        MarkovTextViewStringAdder stringAdder = new MarkovTextViewStringAdder(text);
        stringAdder.run();
    }


    private class MarkovTextViewStringAdder extends Handler implements Runnable {

        private final String[] markovText;
        private int currentIndex;
        private final int delayTime = 20;

        public MarkovTextViewStringAdder(String markovText) {
            super(Looper.getMainLooper());
            this.markovText = markovText.split("\\s+");
            currentIndex = 0;
        }

        private MarkovTextViewStringAdder(String[] markovText, int nextIndex) {
            super(Looper.getMainLooper());
            this.markovText = markovText;
            currentIndex = nextIndex;
        }

        @Override
        public void run() {
            TextView markovTextView = (TextView) findViewById(R.id.markov_text_view);
            String currentText = markovTextView.getText().toString();
            markovTextView.setText(currentText + " " + markovText[currentIndex]);
            currentIndex += 1;

            if (currentIndex < markovText.length) {
                this.postDelayed(this, delayTime);
            }
        }
    }

}
