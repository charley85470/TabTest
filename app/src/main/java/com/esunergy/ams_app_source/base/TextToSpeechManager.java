package com.esunergy.ams_app_source.base;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

import com.esunergy.ams_app_source.utils.LogUtil;

import java.util.HashMap;
import java.util.Locale;

public class TextToSpeechManager {

    private String LOG_TAG = "TextToSpeechManager";
    private static TextToSpeechManager instance;
    private TTSProgressListener mTTSProgListener;
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
            //tts.setLanguage(_locale);
            //ttsListener.onInit(TextToSpeech.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public TextToSpeechManager setTTSProgressListener(TTSProgressListener listener) {
        mTTSProgListener = listener;
        return this;
    }

    public TextToSpeechManager setLanguage(Locale locale) {
        _locale = locale;
        return this;
    }

    public void destroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }

    public void addQueue(String text) {
        if (isLoaded) {
            tts.speak(text, TextToSpeech.QUEUE_ADD, null, text);
        } else {
            LogUtil.LOGE(LOG_TAG, "TTS Not Initialized");
        }
    }

    public void initQueue(String text) {
        if (isLoaded) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, text);
        } else {
            LogUtil.LOGE(LOG_TAG, "TTS Not Initialized");
        }
    }

    private TextToSpeech.OnInitListener ttsListener = new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            if (status == TextToSpeech.SUCCESS) {
                tts.setOnUtteranceProgressListener(upListener);

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
            LogUtil.LOGI(LOG_TAG, "OnStart UtteranceId=" + utteranceId);
        }

        @Override
        public void onDone(String utteranceId) {
            LogUtil.LOGI(LOG_TAG, "OnDone UtteranceId=" + utteranceId);
            mTTSProgListener.onDone();
        }

        @Override
        public void onError(String utteranceId) {
            LogUtil.LOGE(LOG_TAG, "OnError UtteranceId=" + utteranceId);
        }
    };

    public interface TTSProgressListener {
        void onDone();
    }
}
