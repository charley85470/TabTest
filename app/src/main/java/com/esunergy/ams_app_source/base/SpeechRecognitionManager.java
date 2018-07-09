package com.esunergy.ams_app_source.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import com.activeandroid.util.Log;
import com.esunergy.ams_app_source.utils.LogUtil;

import java.util.ArrayList;

public class SpeechRecognitionManager {

    private String LOG_TAG = "SpeechRecognitionManager";
    private static SpeechRecognitionManager instance;
    private MyRecognitionListener listener;
    private Context ctx;
    private SpeechRecognizer speech;
    private Intent speechIntent;
    private SpeechListener speechListener;
    private boolean isExecuting = false;    // 用於辨識是否已關閉此功能，若無則會持續進行語音監聽

    private SpeechRecognitionManager() {
        listener = new MyRecognitionListener();

        speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechIntent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, "zh-TW");
        speechIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getClass().getPackage().getName());
        speechIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
    }

    public static SpeechRecognitionManager getInstance(Context ctx) {
        if (instance == null) {
            instance = new SpeechRecognitionManager();
            instance.setContext(ctx);
        }
        return instance;
    }

    public void setSpeechListener(SpeechListener speechListener) {
        this.speechListener = speechListener;
    }

    public void setContext(Context ctx) {
        this.ctx = ctx;
    }

    public void startListening() {
        isExecuting = true;
        speech = SpeechRecognizer.createSpeechRecognizer(ctx);
        speech.setRecognitionListener(listener);
        speech.startListening(speechIntent);
    }

    public void stopListening() {
        isExecuting = false;
        speech.stopListening();
        speech.cancel();
        speech.destroy();
    }

    class MyRecognitionListener implements RecognitionListener {
        @Override
        public void onReadyForSpeech(Bundle params) {
            LogUtil.LOGD(LOG_TAG, "onReadyForSpeech");
        }

        @Override
        public void onBeginningOfSpeech() {
            LogUtil.LOGD(LOG_TAG, "onBeginningOfSpeech");
            speechListener.onBeginningOfSpeech();
        }

        @Override
        public void onRmsChanged(float rmsdB) {

        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            LogUtil.LOGD(LOG_TAG, "onBufferReceived");
        }

        @Override
        public void onEndOfSpeech() {
            LogUtil.LOGD(LOG_TAG, "onPartialResults");
        }

        @Override
        public void onError(int error) {
            String errorMessage = getErrorText(error);
            LogUtil.LOGD(LOG_TAG, "FAILED " + errorMessage);
            speechListener.onSpeechError(error);

            if (error != SpeechRecognizer.ERROR_CLIENT && error != SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS) {
                speech.startListening(speechIntent);
            }
        }

        @Override
        public void onResults(Bundle results) {
            ArrayList<String> matches = new ArrayList<>();
            if (results != null) {
                matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                matches = matches == null ? new ArrayList<String>() : matches;
            }

            StringBuilder text = new StringBuilder();
            for (String result : matches) {
                text.append(result).append("\n");
            }
            LogUtil.LOGD(LOG_TAG, text.toString());

            speechListener.onSpeechResults(matches);

            if (isExecuting) {
                speech.startListening(speechIntent);
            }
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            LogUtil.LOGD(LOG_TAG, "onPartialResults");
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
            LogUtil.LOGD(LOG_TAG, "onEvent");
        }

        private String getErrorText(int errorCode) {
            String message;
            switch (errorCode) {
                case SpeechRecognizer.ERROR_AUDIO:
                    message = "Audio recording error";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "Client side error";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "Insufficient permissions";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "Network error";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "Network timeout";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "No match";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "RecognitionService busy";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "error from server";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "No speech input";
                    break;
                default:
                    message = "Didn't understand, please try again.";
                    break;
            }
            return message;
        }
    }

    public interface SpeechListener {
        void onBeginningOfSpeech();

        void onSpeechResults(ArrayList<String> results);

        void onSpeechError(int error);
    }
}
