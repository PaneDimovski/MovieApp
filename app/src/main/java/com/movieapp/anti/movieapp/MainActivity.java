package com.movieapp.anti.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.movieapp.anti.movieapp.Activitys.Favorites;
import com.movieapp.anti.movieapp.Activitys.Login;
import com.movieapp.anti.movieapp.Activitys.PeopleActivity;
import com.movieapp.anti.movieapp.Activitys.Rated;
import com.movieapp.anti.movieapp.Activitys.Watchlist;
import com.movieapp.anti.movieapp.Adapters.AdapterView;
import com.movieapp.anti.movieapp.Fragments.NowPlaying;
import com.movieapp.anti.movieapp.Fragments.Popular;
import com.movieapp.anti.movieapp.Fragments.TopRated;
import com.movieapp.anti.movieapp.Fragments.Upcoming;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.pager)
    public ViewPager pager;

    @BindView(R.id.tablayout)
    public TabLayout tabLayout;

    public AdapterView adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);


        setUpViewPager(pager);
        tabLayout.setupWithViewPager(pager);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }


    private void setUpViewPager(ViewPager pager) {
        adapter = new AdapterView(this.getSupportFragmentManager());
        adapter.addFragment(new Popular(), "POPULAR");
        adapter.addFragment(new TopRated(), "TOP RATED ");
        adapter.addFragment(new Upcoming(), "UPCOMING");
        adapter.addFragment(new NowPlaying(), "NOW PLAYING  ");

        pager.setAdapter(adapter);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        int position = 0;

        if (id == R.id.explore) {

        } else if (id == R.id.favo) {
            Intent intent = new Intent(MainActivity.this,Favorites.class);
            startActivity(intent);

        } else if (id == R.id.rated) {
            Intent intent = new Intent(MainActivity.this,Rated.class);
            startActivity(intent);
        } else if (id == R.id.watchlist) {
            Intent intent = new Intent(MainActivity.this,Watchlist.class);
            startActivity(intent);
        } else if (id == R.id.people) {
            Intent intent = new Intent(MainActivity.this,PeopleActivity.class);
            startActivity(intent);

        } else if (id == R.id.logout) {
            Intent intent = new Intent(MainActivity.this,Login.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
