package com.esunergy.ams_app_source.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import com.esunergy.ams_app_source.utils.LogUtil;

import java.util.ArrayList;

public class SpeechRecognitionManager {

    private String LOG_TAG = "SpeechRecognitionManager";
    private static SpeechRecognitionManager instance;
    private MyRecognitionListener mRgnListener;
    private Context mContext;
    private SpeechRecognizer mSphRecognizer;
    private Intent mSphIntent;
    private SpeechListener mSphListener;
    private boolean isExecuting = false;    // 用於辨識是否已關閉此功能，若無則會持續進行語音監聽

    private SpeechRecognitionManager() {
        mRgnListener = new MyRecognitionListener();

        mSphIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSphIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSphIntent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, "zh-TW");
        mSphIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getClass().getPackage().getName());
        mSphIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
    }

    public static SpeechRecognitionManager getInstance(Context ctx) {
        if (instance == null) {
            instance = new SpeechRecognitionManager();
            instance.setContext(ctx);
        }
        return instance;
    }

    public SpeechRecognitionManager setSpeechListener(SpeechListener mSpeechLst) {
        this.mSphListener = mSpeechLst;
        return this;
    }

    public void setContext(Context ctx) {
        this.mContext = ctx;
    }

    public void startListening() {
        isExecuting = true;
        mSphRecognizer = SpeechRecognizer.createSpeechRecognizer(mContext);
        mSphRecognizer.setRecognitionListener(mRgnListener);
        mSphRecognizer.startListening(mSphIntent);
    }

    public void stopListening() {
        isExecuting = false;
        if (mSphRecognizer != null) {
            mSphRecognizer.stopListening();
            mSphRecognizer.cancel();
            mSphRecognizer.destroy();
        }
    }

    class MyRecognitionListener implements RecognitionListener {
        @Override
        public void onReadyForSpeech(Bundle params) {
            LogUtil.LOGD(LOG_TAG, "onReadyForSpeech");
        }

        @Override
        public void onBeginningOfSpeech() {
            LogUtil.LOGD(LOG_TAG, "onBeginningOfSpeech");
            mSphListener.onBeginningOfSpeech();
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
            mSphListener.onSpeechError(error);

            if (error != SpeechRecognizer.ERROR_CLIENT && error != SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS) {
                mSphRecognizer.startListening(mSphIntent);
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

            mSphListener.onSpeechResults(matches);

            if (isExecuting) {
                mSphRecognizer.startListening(mSphIntent);
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
                    message = "No mSphRecognizer input";
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
