package com.example.devineni.foodtracker;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by devineni on 6/21/2016.
 */
public class History extends AppCompatActivity {
    android.support.v7.widget.Toolbar toolbar;
    pageAdapter pageadapter;
    ViewPager viewpager;
    SlidingTabLayout tabs;
    static int counter = 1;
    String navigationitems[] = new String[0];
    String foodnames[];
    DrawerLayout drawerlayout;
    ListView listview;
    String foodname, quantity, units;
    ListView listview1;
    public ActionBarDrawerToggle actionbartoogle;
    CharSequence tabnames[] = {"Breakfast", "Lunch", "Snacks", "Dinner"};
    int tabscount = 4;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("dsc11111111111");
        super.onCreate(savedInstanceState);
        // getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.mainpagelayout);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Food Entries");
        Intent proceed = getIntent();
        viewpager = (ViewPager) findViewById(R.id.pager);
        navigationitems = getResources().getStringArray(R.array.navigationlist);
        drawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listview = (ListView) findViewById(R.id.left_drawer);
        listview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navigationitems));
        listview.setOnItemClickListener(new navigationitemclicklistener());
        listview.setBackgroundColor(Color.WHITE);
        System.out.println("Mainpage");
        Intent i = null;
        pageadapter = new pageAdapter(getSupportFragmentManager(), tabnames, tabscount, i);
        viewpager.setAdapter(pageadapter);
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setdrawer();
        //tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }

            @Override
            public int getDividerColor(int position) {
                return 0;
            }
        });
        tabs.setViewPager(viewpager);
    }

    @Override
    protected void onPostCreate (Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        actionbartoogle.syncState();
    }

    private void setdrawer()
    {
        actionbartoogle = new ActionBarDrawerToggle(this, drawerlayout, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
                CharSequence title = "Options";
                getSupportActionBar().setTitle(title);
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view)
            {
                super.onDrawerClosed(view);
                CharSequence title = "Home";
                getSupportActionBar().setTitle(title);
                invalidateOptionsMenu();
            }
        };
        actionbartoogle.setDrawerIndicatorEnabled(true);
        drawerlayout.setDrawerListener(actionbartoogle);
    }

    class pageAdapter extends FragmentStatePagerAdapter {
        CharSequence tabnames[];
        int numberoftabs;
        Intent getintent;
        public pageAdapter(android.support.v4.app.FragmentManager fm, CharSequence tabnames[], int numberoftabs,Intent i) {
            super(fm);
            this.tabnames = tabnames;
            this.numberoftabs = numberoftabs;
            getintent = i;
        }

        public CharSequence getPageTitle(int position)
        {
            return tabnames[position];
        }
        @Override
        public Fragment getItem(int position)
        {
            switch (position) {
                case 0:
                    Breakfast breakfast;
                    breakfast = new Breakfast("history");
                    return breakfast;
                case 1:
                    Lunch lunch;
                    lunch = new Lunch("lunch");
                    return lunch;
                case 2:
                    Snacks snacks;
                    snacks = new Snacks("snacks");
                    return snacks;
                case 3:
                    Dinner dinner;
                    dinner = new Dinner("dinner");
                    return dinner;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    private class navigationitemclicklistener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selecteditem(position);
        }

        public void selecteditem(int pos) {
            switch (pos) {
                case 0:
                    Intent i = new Intent(getApplicationContext(), MainpageActivity.class);
                    startActivity(i);
                    break;
                case 1:
                    Intent i1 = new Intent(getApplicationContext(), History.class);
                    startActivity(i1);
                    break;
                case 2:
                    Intent i2 = new Intent(getApplicationContext(), Progress.class);
                    startActivity(i2);
                    break;
                case 3:
                    Intent i3 = new Intent(getApplicationContext(), Settings.class);
                    startActivity(i3);
                    break;
            }
        }
    }
}
