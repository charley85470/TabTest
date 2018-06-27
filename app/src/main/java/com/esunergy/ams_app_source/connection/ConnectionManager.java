package com.esunergy.ams_app_source.connection;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.esunergy.ams_app_source.R;
import com.esunergy.ams_app_source.base.GsonUTCDateAdapter;
import com.esunergy.ams_app_source.utils.CommHelper;
import com.esunergy.ams_app_source.utils.DialogUtil;
import com.esunergy.ams_app_source.utils.LogUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ConnectionManager {

    public static final String TAG = "ConnectionManager";

    public static Gson gson = new GsonBuilder().disableHtmlEscaping()
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(Date.class, new GsonUTCDateAdapter())
            .setPrettyPrinting()
            .create();

    private final int connectTimeout = 30;
    private final int readTimeout = 30;

    private ConnectionTask mConnectionTask;

    private Context mContext;

    private static ConnectionManager instance;

    public static ConnectionManager getInstance(Context ctx) {
        if (instance == null) {
            synchronized (ConnectionManager.class) {
                if (instance == null) {
                    instance = new ConnectionManager();
                    instance.setContext(ctx);
                }
            }
        }
        return instance;
    }

    public void setContext(Context cxt) {
        this.mContext = cxt;
    }

    public boolean checkInternet() {
        if (mContext == null) {
            return false;
        }
        ConnectivityManager connManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connManager.getActiveNetworkInfo();

        if (info == null || !info.isConnected() || !info.isAvailable()) {
            return false;
        } else {
            return true;
        }
    }

    public void sendPost(ConnectionService connectionService, String jsonString, ConnectionListener listener, boolean isShowDialog) {
        mConnectionTask = new ConnectionTask(ConnectionType.POST, connectionService, "", jsonString, listener, isShowDialog);
        mConnectionTask.execute();
    }

    public void sendPut(ConnectionService connectionService, String params, String jsonString, ConnectionListener listener, boolean isShowDialog) {
        mConnectionTask = new ConnectionTask(ConnectionType.PUT, connectionService, params, jsonString, listener, isShowDialog);
        mConnectionTask.execute();
    }


    public void sendGet(ConnectionService connectionService, ConnectionListener listener, boolean isShowDialog) {
        mConnectionTask = new ConnectionTask(ConnectionType.GET, connectionService, "", null, listener, isShowDialog);
        mConnectionTask.execute();
    }


    public void sendGet(ConnectionService connectionService, String params, ConnectionListener listener, boolean isShowDialog) {
        mConnectionTask = new ConnectionTask(ConnectionType.GET, connectionService, params, null, listener, isShowDialog);
        mConnectionTask.execute();
    }

    private enum ConnectionType {
        POST,
        GET,
        PUT
    }

    private class ConnectionTask extends AsyncTask<Void, Void, String> {
        private HttpURLConnection conn;
        private ConnectionType connectionType;
        private ConnectionService connectionService;
        private String data;
        private ConnectionListener listener;
        private String urlParams;
        private Dialog mDialog;
        private String result;
        private boolean showDialog;

        public ConnectionTask(ConnectionType connectionType, ConnectionService connectionService, String urlParams, String data, ConnectionListener listener, boolean showDialog) {
            this.connectionType = connectionType;
            this.connectionService = connectionService;
            this.urlParams = urlParams;
            this.data = data;
            this.listener = listener;
            this.showDialog = showDialog;

            mDialog = DialogUtil.createProgressDialog(mContext);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (showDialog) {
                showLoadingDialog();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            if (!checkInternet()) {
                return "Error:" + mContext.getString(R.string.internet_error);
            }
            try {

                String targetUrl = connectionService.getURL();

                if (urlParams != null) {
                    targetUrl += urlParams;
                }

                LogUtil.LOGI("====Request URL====", targetUrl);
                if (data != null) {
                    LogUtil.LOGI("====Request Data====", data);
                }

                conn = (HttpURLConnection) new URL(targetUrl).openConnection();
                conn.setUseCaches(false);
                conn.setReadTimeout(readTimeout * 1000);
                conn.setConnectTimeout(connectTimeout * 1000);
                conn.setRequestMethod(connectionType.toString());
                conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

                if (connectionType == ConnectionType.POST && data != null) {
                    byte[] b = data.getBytes("UTF-8");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setFixedLengthStreamingMode(b.length);
                    DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
                    outputStream.write(b);
                    outputStream.flush();
                    outputStream.close();
                } else if (connectionType == ConnectionType.PUT && data != null) {
                    byte[] b = data.getBytes("UTF-8");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setUseCaches(false);
                    conn.setFixedLengthStreamingMode(b.length);
                    DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
                    outputStream.write(b);
                    outputStream.flush();
                    outputStream.close();
                }


                String respMessage = conn.getResponseMessage();
                int statusCode = conn.getResponseCode();

                LogUtil.LOGI(TAG, "====Response Http statusCode=" + statusCode + "," + respMessage);

                Map<String, List<String>> map = conn.getHeaderFields();

                LogUtil.LOGI(TAG, "====Response Header...");
                for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                    LogUtil.LOGI(TAG, "Key : " + entry.getKey() + " ,Value : " + entry.getValue());
                }

                if (statusCode >= 200 && statusCode < 300) {

                    InputStream inputStreamObj = (InputStream) conn.getContent();
                    if (inputStreamObj != null) {
                        result = CommHelper.convertStreamToString(inputStreamObj);
                        LogUtil.LOGI(TAG, "==========200==Response============");

                        if (result.length() > 2000) {
                            LogUtil.LOGI(TAG, "result.length = " + result.length());
                            int chunkCount = result.length() / 2000;     // integer division
                            for (int i = 0; i <= chunkCount; i++) {
                                int max = 2000 * (i + 1);
                                if (max >= result.length()) {
                                    LogUtil.LOGI(TAG, "chunk " + i + " of " + chunkCount + "＝ " + result.substring(2000 * i));
                                } else {
                                    LogUtil.LOGI(TAG, "chunk " + i + " of " + chunkCount + "＝ " + result.substring(2000 * i, max));
                                }
                            }
                        } else {
                            LogUtil.LOGI(TAG, result);
                        }
                    } else {
                        result = "";
                    }
                } else if (statusCode == 400) {
                    InputStream inputStreamObj = conn.getErrorStream();
                    if (inputStreamObj != null) {
                        result = CommHelper.convertStreamToString(inputStreamObj);
                        LogUtil.LOGI(TAG, "==========400==Response============");
                        LogUtil.LOGI(TAG, result);
                        JsonElement jsonElement = new JsonParser().parse(result);
                        String message = "P:400" + jsonElement.getAsJsonObject().get("Message").getAsString();
                        LogUtil.LOGI(TAG, "message=" + message);
                        return message;
                    } else {
                        return "P:400" + mContext.getString(R.string.internet_error);
                    }
                } else if (statusCode == 412) {
                    return "P:412" + mContext.getString(R.string.connection_err_412);
                } else if (statusCode == 500) {
                    return "P:500" + mContext.getString(R.string.connection_err_500);
                } else {
                    InputStream errStream = conn.getErrorStream();
                    if (errStream != null) {
                        result = CommHelper.convertStreamToString(errStream);
                        LogUtil.LOGI(TAG, "===========errStream=Response============");
                        LogUtil.LOGI(TAG, result);
                    }
                    return "P:998" + mContext.getString(R.string.internet_error);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "P:999" + mContext.getString(R.string.internet_error);
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (showDialog) {
                closeLoadingDialog();
            }
            if (result != null && (result.startsWith("Error:") || result.startsWith("P:"))) {
                listener.onConnectionError(connectionService, result);
            } else if (result != null && !result.equals("")) {
                listener.onConnectionResponse(connectionService, result);
            } else if (result.equals("")) {
                listener.onConnectionResponse(connectionService, result);
            }
        }

        private void showLoadingDialog() {
            if (!mDialog.isShowing()) {
                mDialog.show();
            }
        }

        private void closeLoadingDialog() {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
    }

    public interface ConnectionListener {

        public void onConnectionResponse(ConnectionService service, String result);

        public void onConnectionError(ConnectionService service, String errorMsg);
    }
}
