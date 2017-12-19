package com.danklemme.trumpov;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dan on 10/27/2017.
 */

public class MarkovGenerationFragment extends Fragment {

    private MarkovChain markovChain;
    private int numWords = 200;
    private Database db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_markov_generation, container, false);
        initButtons(v);
        onClickMakeTextButton();
        return v;
    }

    @Override
    public void onAttach(Context c) {
        super.onAttach(c);
        loadChain(c);
        db = new Database(getContext());
    }

    private void loadChain(Context c) {
        ArrayList<String> assets = new TextAssetLoader().loadAssets(c, "campaign_speeches");
        GeneratableMarkov generatableMarkov = new StringGeneratableMarkov(assets);
        MarkovIterator iterator = new SimpleMarkovIterator(generatableMarkov, 2);
        markovChain = new PeriodEndMarkovChain(new CapitalStartMarkovChain(
                        new SimpleMarkovChain(iterator)));
    }

    private void initButtons(View rootView) {
        View button = rootView.findViewById(R.id.button_make_text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickMakeTextButton();
            }
        });
        button = rootView.findViewById(R.id.button_save_markov_text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSaveTextButton();
            }
        });

    }

    private void generateMarkovText() {
        List<String> markovText = markovChain.chain("", numWords);
        MarkovTextViewStringHandler stringAdder = new MarkovTextViewStringHandler(this, markovText);
        stringAdder.run();
    }

    private void onClickMakeTextButton() {
        View rootView = getView();
        if (rootView != null) {
            ((TextView) rootView.findViewById(R.id.markov_text_view)).setText("");  // Clear previous text
            generateMarkovText();
        }
    }

    private void onClickSaveTextButton() {
        View v = getView();
        if (v  != null) {
            TextView textView = (TextView) v.findViewById(R.id.markov_text_view);
            db.putText(textView.getText().toString(),
                    getString(R.string.text_type_campaign_speech));
        }
    }

    private void addMarkovWord(String newWord) {
        View rootView = getView();
        if (rootView !=null) {
            TextView markovTextView = (TextView) rootView.findViewById(R.id.markov_text_view);
            String currentText = markovTextView.getText().toString();
            markovTextView.setText(currentText + " " + newWord);
        }
    }

    private static class MarkovTextViewStringHandler extends Handler implements Runnable {

        private final List<String> markovText;
        private int currentIndex;
        private final int delayTime = 1;
        WeakReference<MarkovGenerationFragment> markovFrag;

        MarkovTextViewStringHandler( MarkovGenerationFragment frag, List<String> markovText) {
            super(Looper.getMainLooper());
            this.markovText = markovText;
            currentIndex = 0;
            markovFrag = new WeakReference<>(frag);
        }

        @Override
        public void run() {
            markovFrag.get().addMarkovWord(markovText.get(currentIndex));
            currentIndex += 1;
            if (currentIndex < markovText.size()) {
                this.postDelayed(this, delayTime);
            }
        }

    }
}
