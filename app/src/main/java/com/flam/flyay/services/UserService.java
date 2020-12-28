package com.flam.flyay.services;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flam.flyay.MainActivity;
import com.flam.flyay.model.Event;
import com.flam.flyay.model.User;
import com.flam.flyay.util.ConverterFromJsonToModel;
import com.flam.flyay.util.MockServerUrl;
import com.flam.flyay.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private Context context;
    private AppRequest appRequest;

    public UserService(Context context) {
        this.context = context;
        this.appRequest = new AppRequest();
    }

    public void signin(JSONObject params, final ServerCallback callback) {
        Log.d(".UserService", "POST - signin");

        appRequest.jsonObjectPOSTRequest(context, MockServerUrl.SIGNIN_OK.url, params, new ServerCallback() {
            @Override
            public void onSuccess(Object result) {
                Utils.getStatusResponse((JSONObject) result, callback);
            }
        });
    }
    public void getUserById(JSONObject params, final ServerCallback callback) {
        Log.d(".UserService", "GET - UserById");
        appRequest.jsonObjectGETRequest(context, MockServerUrl.PROFILE_USER.url, params, new ServerCallback() {
            @Override
            public void onSuccess(Object result) {
                getDetailsUser((JSONObject) result, callback);
            }
        });
    }
    public void getDetailsUser(JSONObject requestResult, final ServerCallback callback) {
        User user = new User();
        JSONObject containerResponse = new JSONObject();
        try {
            containerResponse = requestResult.getJSONObject("return");
            user = ConverterFromJsonToModel.converterFromJsonToUser(containerResponse);
            Log.d(".ProfileFragment", user.startToString());
            callback.onSuccess(user);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
