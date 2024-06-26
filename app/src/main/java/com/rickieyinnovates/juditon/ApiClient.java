package com.rickieyinnovates.juditon;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ApiClient {
    private static final String TAG = "MyApiRequest";

    private final RequestQueue requestQueue;
    private final ApiDataManager apiDataManager;

    public ApiClient(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        apiDataManager = ApiDataManager.getInstance();
    }

    public void login(final String username, final String password, final String url, final LoginCallback callback) {
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("username", username);
            requestBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Endpoints.BASE_URL + url, requestBody,
                response -> {
                    try {
                        String authToken = response.getString("token");
                        String name = response.getString("username");
                        String email = response.getString("email");

                        apiDataManager.setAuthToken(authToken);
                        apiDataManager.setUsername(name);
                        apiDataManager.setEmail(email);

                        Log.d(TAG, "login response: " + response);

                        callback.onLoginSuccess(authToken);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        // Notify the callback of an error
                        callback.onLoginError("Invalid response from the server");
                    }
                },
                error -> {
                    try {
                        Log.e(TAG, error.getMessage());
                        callback.onLoginError(error.getLocalizedMessage());
                    } catch (Exception e) {
                        Log.e(TAG, e.getLocalizedMessage());
                        callback.onLoginError(error.getLocalizedMessage());
                    }

                });

        // Add the request to the queue
        requestQueue.add(jsonRequest);
    }

    public void makeAuthenticatedGetRequest(final Context context, final String url, final Callback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Endpoints.BASE_URL + url,
                callback::onSuccess,
                callback::onError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + apiDataManager.getAuthToken());
                return headers;
            }
        };

        requestQueue.add(stringRequest);
    }

    public void makeAuthenticatedPostRequest(final JSONObject requestBody, final String url, final Callback callback) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Endpoints.BASE_URL + url, requestBody,
                response -> {
                    Log.i(TAG, "makeAuthenticatedPostRequest: " + response);
                    callback.onSuccess(String.valueOf(response));
                },
                error -> {
                    Log.e(TAG, error.getMessage());
                    callback.onError(error);
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + apiDataManager.getAuthToken());
                return headers;
            }
        };

        requestQueue.add(jsonRequest);
    }

    public interface Callback {
        void onSuccess(String response);

        void onError(VolleyError error);
    }

    public interface LoginCallback {
        void onLoginSuccess(String authToken);

        void onLoginError(String errorMessage);
    }
}