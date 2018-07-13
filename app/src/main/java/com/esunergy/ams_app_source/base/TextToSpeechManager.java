package com.esunergy.ams_app_source.base;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

import com.esunergy.ams_app_source.utils.LogUtil;

import java.util.Locale;

public class TextToSpeechManager {

    private String LOG_TAG = "TextToSpeechManager";
    private static TextToSpeechManager instance;
    private TextToSpeech tts;
    private Context _ctx;
    private boolean isLoaded = false;
    private Locale _locale;

    public static TextToSpeechManager getInstance(Context ctx) {
        if (instance == null) {
            instance = new TextToSpeechManager();
        }
        instance.setContext(ctx);
        return instance;
    }

    public void setContext(Context ctx) {
        this._ctx = ctx;
    }

    public TextToSpeechManager init() {
        try {
            tts = new TextToSpeech(_ctx, ttsListener);
            ttsListener.onInit(TextToSpeech.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public TextToSpeechManager setLanguage(Locale locale) {
        _locale = locale;
        return this;
    }

    public void destroy() {
        tts.shutdown();
    }

    public void addQueue(String text) {
        if (isLoaded) {
            tts.speak(text, TextToSpeech.QUEUE_ADD, null, null);
        } else {
            LogUtil.LOGE(LOG_TAG, "TTS Not Initialized");
        }
    }

    public void initQueue(String text) {
        if (isLoaded) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            LogUtil.LOGE(LOG_TAG, "TTS Not Initialized");
        }
    }

    private TextToSpeech.OnInitListener ttsListener = new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            if (status == TextToSpeech.SUCCESS) {
                tts.setOnUtteranceProgressListener(upListener);
                initQueue("");  // 喚醒tts

                int result = -1;
                if (_locale != null) {
                    result = tts.setLanguage(_locale);
                } else {
                    // 預設英文
                    result = tts.setLanguage(Locale.US);
                }

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    LogUtil.LOGE(LOG_TAG, "This Language \"" + _locale.getCountry() + "\" is not supported");
                } else {
                    isLoaded = true;
                }
            } else {
                LogUtil.LOGE(LOG_TAG, "TTS Not Initialized");
            }
        }
    };

    private UtteranceProgressListener upListener = new UtteranceProgressListener() {
        @Override
        public void onStart(String utteranceId) {

        }

        @Override
        public void onDone(String utteranceId) {

        }

        @Override
        public void onError(String utteranceId) {

        }
    };
}
