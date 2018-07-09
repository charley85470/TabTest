package com.esunergy.ams_app_source.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.esunergy.ams_app_source.R;
import com.esunergy.ams_app_source.base.SpeechRecognitionManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpeechAPITestFragment extends BaseFragment implements SpeechRecognitionManager.SpeechListener {

    private String LOG_TAG = "Speech";
    private Context ctx;
    private View topLayoutView;
    private Button btn_browse, start, stop;
    private SpeechRecognizer speech;
    private Switch aSwitch;
    private SpeechRecognitionManager manager;

    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

    public SpeechAPITestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ctx = getActivity();
        topLayoutView = LayoutInflater.from(ctx).inflate(R.layout.fragment_speech_apitest, container, false);

        btn_browse = topLayoutView.findViewById(R.id.browse_button);
        start = topLayoutView.findViewById(R.id.start);
        stop = topLayoutView.findViewById(R.id.stop);
        aSwitch = topLayoutView.findViewById(R.id.switch1);

        manager = SpeechRecognitionManager.getInstance(ctx).initSpeechRecognizer(SpeechAPITestFragment.this);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    manager.startListening();
                } else {
                    manager.stopListening();
                }
            }
        });

        return topLayoutView;
    }

    @Override
    public void onSpeechError(int error) {
    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onSpeechResults(ArrayList<String> results) {
        String text = "";
        for (String result : results) {
            text += result + "\n";
        }
        Toast.makeText(ctx, text, Toast.LENGTH_LONG).show();
    }
}
