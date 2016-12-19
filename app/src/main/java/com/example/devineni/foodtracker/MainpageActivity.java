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
import android.widget.ImageButton;
import android.widget.ListView;

/**
 * Created by devineni on 6/20/2016.
 */
public class MainpageActivity extends AppCompatActivity {
    android.support.v7.widget.Toolbar toolbar;
    pageAdapter pageadapter;
    ViewPager viewpager;
    SlidingTabLayout tabs;
    static int counter = 1;
    ImageButton imagebutton;
    String navigationitems[] = new String[0];
    String foodnames[];
    DrawerLayout drawerlayout;
    ListView listview;
    int index;
    String foodname, quantity, units,position;
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
            if (savedInstanceState != null)
            {
            System.out.println("on create saved instance state");
            index = savedInstanceState.getInt("fragmentindexpage");
            }
            System.out.println("after 1st oncreate" +  index);
            toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("Food Entries");
            imagebutton = (ImageButton) findViewById(R.id.imageButton);
            imagebutton.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v)
            {
                Intent newintent = new Intent(getApplicationContext(),FoodServing.class);
                index = viewpager.getCurrentItem();
                if(index == 0)
                {
                    newintent.putExtra("foodpart","Breakfast");
                }
                if(index == 1)
                {
                    newintent.putExtra("foodpart","Lunch");
                }
                if(index == 2)
                {
                    newintent.putExtra("foodpart","Snacks");
                }
                if(index == 3)
                {
                    newintent.putExtra("foodpart","Dinner");
                }
                startActivity(newintent);
            }
        });
            Intent proceed = getIntent();
            viewpager = (ViewPager) findViewById(R.id.pager);
            navigationitems = getResources().getStringArray(R.array.navigationlist);
            drawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            listview = (ListView) findViewById(R.id.left_drawer);
            listview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navigationitems));
            listview.setOnItemClickListener(new navigationitemclicklistener());
            listview.setBackgroundColor(Color.WHITE);
            if(proceed.getStringExtra("username") != null)
            {
                System.out.println("Mainpage22");
                pageadapter = new pageAdapter(getSupportFragmentManager(), tabnames, tabscount);
            }
        else
            {
                System.out.println("Mainpage");
                Intent i = getIntent();
                //position = i.getStringExtra("position");
                pageadapter = new pageAdapter(getSupportFragmentManager(), tabnames, tabscount, i);
            }
            viewpager.setAdapter(pageadapter);
            viewpager.setOffscreenPageLimit(1);
            tabs = (SlidingTabLayout) findViewById(R.id.tabs);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setdrawer();
            tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer()
            {
                @Override
                public int getIndicatorColor(int position)
                {
                    return getResources().getColor(R.color.tabsScrollColor);
                }
                @Override
                public int getDividerColor(int position)
                {
                    return 0;
                }
            });
            tabs.setViewPager(viewpager);
        }

    public void onstart()
    {
       //System.out.println("application started");
    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
       // System.out.println("saved instance state " + index);
        savedInstanceState.putInt("fragmentindexpage", index);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        //System.out.println("restore saved instance state " + index);
        index = savedInstanceState.getInt("fragmentindexpage");
    }
        public void onPause()
        {
          //  System.out.println("Mainpageactivity onpause");
            super.onPause();
            index = viewpager.getCurrentItem();
            //System.out.println(index);
        }
        public void onResume()
        {
            super.onResume();
           // System.out.println("Mainpageactivity onresume");
         //   System.out.println(index);
            viewpager.setCurrentItem(index);
        }
        @Override
        protected void onPostCreate (Bundle savedInstanceState)
        {
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
        Intent getintent = null;
        public pageAdapter(android.support.v4.app.FragmentManager fm, CharSequence tabnames[], int numberoftabs) {
            super(fm);
            this.tabnames = tabnames;
            this.numberoftabs = numberoftabs;
        }
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
            Breakfast breakfast;
            Lunch lunch;
            Snacks snacks;
            Dinner dinner;
            //System.out.println(position);
            if(position == 0)
            {
                if(getintent != null)
                {
                 //   System.out.println("Breakfast1");
                    breakfast = new Breakfast(getintent);
                    getintent = null;
                }
                else
                {
                   // System.out.println("Breakfast2");
                    breakfast = new Breakfast();
                }
                return breakfast;
            }
            if(position == 1)
            {
                if(getintent != null)
                {
                    //System.out.println("Lunch1");
                    lunch = new Lunch(getintent);
                    getintent = null;
                }
                else
                {
                    //System.out.println("Lunch2");
                    lunch = new Lunch();

                }
                return  lunch;
            }
            if(position == 2)
            {
                if(getintent != null)
                {
                    //System.out.println("Snacks1");
                    snacks = new Snacks(getintent);
                    getintent = null;
                }
                else
                {
                    //System.out.println("Snacks2");
                    snacks = new Snacks();
                }
                return  snacks;
            }
            if(position == 3)
            {
                if(getintent != null)
                {
                    //System.out.println("Dinner1");
                    dinner = new Dinner(getintent);
                    getintent = null;
                }
                else
                {
                    //System.out.println("Dinner2");
                    dinner = new Dinner();
                }
                return dinner;
            }
          return null;
        }

        @Override
        public int getCount()
        {
            return 4;
        }
    }

    private class navigationitemclicklistener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selecteditem(position);
        }

        public void selecteditem(int pos)
        {
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