package com.flam.flyay;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.flam.flyay.ui.addevent.AddEventFragment;
import com.flam.flyay.ui.home.HomeFragment;
import com.flam.flyay.ui.profile.ProfileFragment;
import com.flam.flyay.ui.search.SearchFragment;
import com.flam.flyay.ui.todo.ToDoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private ActionBar ab;
    private Calendar c;
    private SimpleDateFormat df;
    private String currentDate;
    private Boolean ifhome, ifprofile, iflist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        loadFragment(new HomeFragment());
        navView.setOnNavigationItemSelectedListener(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        //ab.setDisplayHomeAsUpEnabled(true);  toglie la freccia che torna indietro

        c = Calendar.getInstance(TimeZone.getTimeZone("GMT"),Locale.getDefault());
        df = new SimpleDateFormat("dd/MM/yyyy");
        currentDate = df.format(c.getTime());
        ab.setTitle(currentDate);
        ab.setIcon(R.drawable.ic_home_page);
        ifhome = true;
        ifprofile = false;
        iflist = false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.home:
                fragment = new HomeFragment();
                ab.setTitle(currentDate);
                ab.setIcon(R.drawable.ic_home_page);
                ifhome = true;
                invalidateOptionsMenu();
                break;

            case R.id.lens:
                fragment = new SearchFragment();
                ab.setTitle("Search");
                ab.setIcon(R.drawable.ic_search);
                invalidateOptionsMenu();
                break;

            case R.id.plus:
                fragment = new AddEventFragment();
                ab.setTitle("Add event");
                ab.setIcon(R.drawable.ic_add_event);
                invalidateOptionsMenu();
                break;

            case R.id.list:
                fragment = new ToDoFragment();
                ab.setTitle("To do");
                ab.setIcon(R.drawable.ic_to_do);
                iflist = true;
                invalidateOptionsMenu();
                break;

            case R.id.profile:
                fragment = new ProfileFragment();
                ab.setTitle("Profile");
                ab.setIcon(R.drawable.ic_profile);
                ifprofile = true;
                invalidateOptionsMenu();
                break;
        }

        return loadFragment(fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actions_menu, menu);

        if(ifhome){
            for (int i = 0; i < menu.size(); i++) {
                menu.getItem(i).setVisible(false);
                if(menu.getItem(i).getItemId() == R.id.home_options)
                    menu.getItem(i).setVisible(true);
            }
            ifhome = false;
            return true;
        }
        if(ifprofile){
            for (int i = 0; i < menu.size(); i++) {
                menu.getItem(i).setVisible(false);
                if(menu.getItem(i).getItemId() == R.id.action_options)
                    menu.getItem(i).setVisible(true);
            }
            ifprofile = false;
            return true;
        }
        if(iflist){
            for (int i = 0; i < menu.size(); i++) {
                menu.getItem(i).setVisible(false);
                if(menu.getItem(i).getItemId() == R.id.new_todo)
                    menu.getItem(i).setVisible(true);
            }
            iflist = false;
            return true;
        }
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_options:
                return true;
            case R.id.settings:
                //goToSettings();
                return true;
            case R.id.logout:
                Intent intent = new Intent(MainActivity.this, LogInActivity.class);
                startActivity(intent);
                return true;
            case R.id.new_todo:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}