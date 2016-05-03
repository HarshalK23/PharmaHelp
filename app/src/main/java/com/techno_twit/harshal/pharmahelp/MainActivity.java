package com.techno_twit.harshal.pharmahelp;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();
                openFragment(menuItem);
                return true;
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open, R.string.drawer_close){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

    }
    private void openFragment(MenuItem item){

        Fragment fragment=null;
        Class fragmentClass=null;
        FragmentManager fragmentManager=getFragmentManager();

        switch (item.getItemId()){
            /*
            case R.id.profile:
                ProfileFragment profile=new ProfileFragment();
                fragmentManager.beginTransaction().replace(R.id.frame,profile).commit();
                break;
                */
            case R.id.cate:
                CategoryFragment cat=new CategoryFragment();
                fragmentManager.beginTransaction().replace(R.id.frame,cat).commit();
                break;
            case R.id.search:
                SearchFragment search=new SearchFragment();
                fragmentManager.beginTransaction().replace(R.id.frame,search).commit();
                break;
            case R.id.feed:

                FeedbackFragment feed=FeedbackFragment.newInstance();
                fragmentManager.beginTransaction().replace(R.id.frame,feed).commit();
                break;
            //default:
                //fragmentClass= ProfileFragment.class;
        }


        item.setChecked(true);
        setTitle(item.getTitle());

        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pharma_help, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals("Location")) {
            Intent in = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(in);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
