package com.flam.flyay;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.flam.flyay.fragments.ToDoItemsFragment;
import com.flam.flyay.fragments.ToDoListFragment;
import com.flam.flyay.fragments.ToDoNewFragment;
import com.flam.flyay.model.ToDo;
import com.flam.flyay.util.TouchInterceptor;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class ToDoActivity extends AppCompatActivity implements ToDoListFragment.OnToDoListListener {

    private Toolbar toolbar;
    private ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkAppTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
        setContentView(R.layout.activity_to_do);

        RelativeLayout touchInterceptor = (RelativeLayout) findViewById(R.id.touchInterceptor);
        touchInterceptor.setOnTouchListener(new TouchInterceptor(this));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        /*ab.setTitle("To do");
        ab.setIcon(R.drawable.ic_to_do);
        invalidateOptionsMenu();*/

        BottomNavigationView navView = findViewById(R.id.nav_view);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            navView.setBackgroundColor(BLACK);
            toolbar.setBackgroundColor(BLACK);
        }
        else{
            navView.setBackgroundColor(WHITE);
            toolbar.setBackgroundColor(WHITE);
        }
        navView.setSelectedItemId(R.id.list);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){
                switch (menuItem.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.lens:
                        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.plus:
                        startActivity(new Intent(getApplicationContext(), AddEventActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.list:
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        initializeFragments(new ToDoListFragment(),null);
        //invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actions_menu, menu);
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setVisible(false);
            if(menu.getItem(i).getItemId() == R.id.new_todo)
                menu.getItem(i).setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_todo:
                initializeFragments(new ToDoNewFragment(), null);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onToDoListSelected(ToDo toDo) {
        Log.d(".ToDoActivity", toDo.toString());
        initializeFragments(new ToDoItemsFragment(), createParamsItems(toDo));
    }

    private Bundle createParamsItems(ToDo toDo) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("toDo", toDo);

        return bundle;
    }

    private void initializeFragments(Fragment fragment, Bundle params) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragment.setArguments(params);
        fragmentTransaction.replace(R.id.display_todo_list_fragment, fragment, fragment.getClass().getName());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    @Override
    public void onBackPressed() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStackImmediate();
        } else {
            super.onBackPressed();
        }
    }
}