package com.flam.flyay.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.flam.flyay.R;
import com.flam.flyay.model.ToDoItems;

import java.util.ArrayList;
import java.util.List;

public class ToDoItemsAdapter extends BaseAdapter implements ListAdapter {
    private Context context;
    private List<ToDoItems> todo_items;
    private ToDoItems todoItem;

    private CheckBox cb;
    public ToDoItemsAdapter(List<ToDoItems> todo_items, Context context) {
        this.context = context;
        this.todo_items = todo_items;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.to_do_item_adapter, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(todo_items.get(position).getTitle());

        //Handle buttons and add onClickListeners
        ImageButton deleteBtn = (ImageButton) view.findViewById(R.id.delete_btn);
        deleteBtn.setBackgroundColor(Color.TRANSPARENT);
        cb = (CheckBox) view.findViewById(R.id.list_item_checkbox);

        if(todo_items.get(position).isChecked())
            cb.setChecked(true);
        else{
            cb.setChecked(false);
        }

        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CheckBox) view).isChecked()){
                    todo_items.get(position).setChecked(true);
                } else {
                    todo_items.get(position).setChecked(false);
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                todo_items.remove(position); //or some other task
                notifyDataSetChanged();
            }
        });

        return view;
    }

    public void add(String itemText) {
        todoItem = new ToDoItems();
        todoItem.setId(todo_items.size()+1);
        todoItem.setTitle(itemText);
        todoItem.setChecked(false);
        todo_items.add(todoItem);
        Log.d("prova :", todoItem.toString());
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return todo_items.size();
    }

    @Override
    public Object getItem(int position) {
        return todo_items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return todo_items.get(position).getId();
    }
}
