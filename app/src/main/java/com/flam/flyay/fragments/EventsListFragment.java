package com.flam.flyay.fragments;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.flam.flyay.R;
import com.flam.flyay.model.Event;
import com.flam.flyay.model.EventWellness;
import com.flam.flyay.services.EventService;
import com.flam.flyay.services.ServerCallback;
import com.flam.flyay.util.ConverterFromJsonToModel;
import com.flam.flyay.util.MockServerUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class EventsListFragment extends Fragment {
    private EventService service;
    private ConverterFromJsonToModel converterFromJsonToModel;
    private List<Event> events;
    private OnEventsListListener onEventsListListener;

    public interface OnEventsListListener {
        void onEventSelected(Event e);
    }

    public EventsListFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            onEventsListListener = (OnEventsListListener) context;
        } catch(ClassCastException e) {
            throw new ClassCastException("OnEventsListListener interface must be implemented");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        final ListView listView = view.findViewById(R.id.events_list);
        Bundle arguments = getArguments();

        this.service = new EventService(this.getContext());
        this.converterFromJsonToModel = new ConverterFromJsonToModel();
        this.events = new ArrayList<>();
        String currentDate = arguments.getString("currentDate");

        JSONObject params = getParams(currentDate);
        Log.d(".EventsListFragment", "parameters: [currentDate = '" + currentDate + "']");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onEventsListListener.onEventSelected(events.get(position));
            }
        });

        service.getEventsByDay(MockServerUrl.EVENT_DAY.url, params, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                List<String> eventsTitle = new ArrayList<>();
                JSONArray containerResponse;
                try {
                    containerResponse = result.getJSONArray("return");

                    for(int i = 0; i < containerResponse.length(); i ++) {
                        Event event = converterFromJsonToModel.converterFromJsonToEvent(containerResponse.getJSONObject(i));
                        events.add(event);
                        String entry = "";
                        if(event instanceof EventWellness) {
                            EventWellness eventWellness = (EventWellness) event;
                            String startingTime = Double.toString(eventWellness.getStartingTime());
                            String endTime = Double.toString(eventWellness.getEndTime());

                            if(startingTime.length() > 0) {
                                if(startingTime.length() == 4)
                                    startingTime += "0";
                                entry += startingTime + " -";
                            }
                            if(endTime.length() > 0) {
                                if(endTime.length() == 4)
                                    endTime += "0";
                                entry += "" + endTime;
                            }

                            if(entry.length() > 0) {
                                entry += " | ";
                            }
                        }
                        entry += event.getTitle() + " event";
                        eventsTitle.add(entry);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d(".EventsListFragment",events.toString());
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, eventsTitle);
                for(int j = 0; j < adapter.getCount(); j ++) {
                    System.out.println(adapter.getItem(j));
                }
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private JSONObject getParams(String currentDay) {
        JSONObject params = new JSONObject();
        try {
            params.put("currentDay", currentDay);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return params;
    }
}