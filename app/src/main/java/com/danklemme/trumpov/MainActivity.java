package com.danklemme.trumpov;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MarkovChain chain;
    private final int numWords = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadChain();
        initButton();
    }

    private void loadChain() {
        ArrayList<String> assets = new TextAssetLoader().loadAssets(this, "campaign_speeches");
        GeneratableMarkov generatableMarkov = new StringGeneratableMarkov(assets);
        MarkovIterator iterator = new SimpleMarkovIterator(generatableMarkov, 2);
        chain = new SimpleMarkovChain(iterator);
        chain = new CapitalStartMarkovChain(chain);
        chain = new PeriodEndMarkovChain(chain);
    }

    private void initButton() {
        View button = findViewById(R.id.button_make_text);
        button.setOnClickListener(this);
    }

    private void generateMarkovText() {
        String text = chain.chain("", numWords);
        MarkovTextViewStringAdder stringAdder = new MarkovTextViewStringAdder(text);
        stringAdder.run();
    }

    @Override
    public void onClick(View v) {
        ((TextView)findViewById(R.id.markov_text_view)).setText("");  // Clear previous text if any
        generateMarkovText();
    }


    private class MarkovTextViewStringAdder extends Handler implements Runnable {

        private final String[] markovText;
        private int currentIndex;
        private final int delayTime = 1;

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
