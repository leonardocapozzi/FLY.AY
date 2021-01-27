package com.flam.flyay.services;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flam.flyay.model.Event;
import com.flam.flyay.util.ConverterFromJsonToModel;
import com.flam.flyay.util.MockServerUrl;
import com.flam.flyay.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class EventService {
    private Context context;
    private AppRequest appRequest;

    public EventService(Context context) {
        this.context = context;
        this.appRequest = new AppRequest();
    }

    public void getEventsByDay(JSONObject params, final ServerCallback callback) {
        Log.d(".EventService", "GET - EventsByDay");
        appRequest.jsonObjectGETRequest(context, MockServerUrl.EVENT_DAY.url, params, new ServerCallback() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(Object result) {
                getEventsList((JSONObject) result, callback);
            }
        });
    }

    public void getEventsByFilter(JSONObject params, final ServerCallback callback) {
        Log.d(".EventService", "POST - EventsByFilter");
        appRequest.jsonObjectPOSTRequest(context, MockServerUrl.EVENT_DAY.url, params, new ServerCallback() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(Object result) {
                getEventsList((JSONObject) result, callback);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getEventsList(JSONObject requestResult, final ServerCallback callback) {
        List<Event> events = new ArrayList<>();
        JSONArray containerResponse = new JSONArray();
        try {
            containerResponse = requestResult.getJSONArray("return");

            for(int i = 0; i < containerResponse.length(); i ++) {
                JSONObject currentJSONObject = containerResponse.getJSONObject(i);
                Event event = ConverterFromJsonToModel.converterFromJsonToEvent(currentJSONObject);
                Log.d(".EventsListFragment", event.toString());

                events.add(event);
            }
            Utils.orderEventListBy(events, "startingTime");
            System.out.println("AFTER ORDER:\n" + events);
            callback.onSuccess(events);
        } catch (JSONException e) {
            Log.getStackTraceString(e);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getInputFieldBySubcategory(JSONObject requestResult, final ServerCallback callback) {
        List<Event> events = new ArrayList<>();
        JSONArray containerResponse = new JSONArray();
        try {
            containerResponse = requestResult.getJSONArray("return");

            for(int i = 0; i < containerResponse.length(); i ++) {
                JSONObject currentJSONObject = containerResponse.getJSONObject(i);
                Event event = ConverterFromJsonToModel.converterFromJsonToEvent(currentJSONObject);
                Log.d(".EventsListFragment", event.toString());

                events.add(event);
            }
            Utils.orderEventListBy(events, "startingTime");
            System.out.println("AFTER ORDER:\n" + events);
            callback.onSuccess(events);
        } catch (JSONException e) {
            Log.getStackTraceString(e);
        }
    }


}
