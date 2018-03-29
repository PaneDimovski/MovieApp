package com.movieapp.anti.movieapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.movieapp.anti.movieapp.Activitys.DetailsActiviti;
import com.movieapp.anti.movieapp.Activitys.Favorites;
import com.movieapp.anti.movieapp.Activitys.Login;
import com.movieapp.anti.movieapp.Activitys.PeopleActivity;
import com.movieapp.anti.movieapp.Activitys.RatedActivity;
import com.movieapp.anti.movieapp.Activitys.Watchlist;
import com.movieapp.anti.movieapp.Adapters.AdapterView;
import com.movieapp.anti.movieapp.Adapters.ExlorerRecyclerViewAdapter;
import com.movieapp.anti.movieapp.Api.RestApi;
import com.movieapp.anti.movieapp.Fragments.NowPlaying;
import com.movieapp.anti.movieapp.Fragments.Popular;
import com.movieapp.anti.movieapp.Fragments.TopRated;
import com.movieapp.anti.movieapp.Fragments.Upcoming;
import com.movieapp.anti.movieapp.Listeners.OnRowClickListener;
import com.movieapp.anti.movieapp.Models.Movie;
import com.movieapp.anti.movieapp.Models.MovieDataModel;
import com.movieapp.anti.movieapp.Models.User;
import com.movieapp.anti.movieapp.Preferences.PrefererencesManager2;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.pager)
    public ViewPager pager;

    @BindView(R.id.tablayout)
    public TabLayout tabLayout;

    public AdapterView adapter;

    String itemLog = "";
    TextView name3;
    ImageView profile_image;


    @BindView(R.id.editSearch)
    EditText search;

    public Movie film = new Movie();
    MovieDataModel model;
    Handler handler;

    @BindView(R.id.recycle)
    RecyclerView rv;

    ExlorerRecyclerViewAdapter RVadapter;

    User user;
    RestApi api;

    Context context;
    User userActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        context = this;


        setUpViewPager(pager);
        tabLayout.setupWithViewPager(pager);


        SearchListener();

        MainActivity.this.getWindow().setSoftInputMode(

                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN

        );

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

        View view = navigationView.getHeaderView(0);
        Menu menu = navigationView.getMenu();

        final String name2 = PrefererencesManager2.getUser(this);
        String token = PrefererencesManager2.getToken(this);
        MenuItem logout_login = menu.findItem(R.id.login);

        //  final String sessionID = PrefererencesManager2.getSessionID(MainActivity.this);
        View headerView = navigationView.getHeaderView(0);
        name3 = (TextView) headerView.findViewById(R.id.name);
        profile_image = (ImageView) headerView.findViewById(R.id.profile_image);

        final String sessionID = PrefererencesManager2.getSessionID(context);

        profile_image = (ImageView) headerView.findViewById(R.id.profile_image);

        if (sessionID != null && !sessionID.isEmpty())

        {
            api = new RestApi(context);

            Call<User> call = api.getUserDetails2(sessionID);

            call.enqueue(new Callback<User>() {

                @Override

                public void onResponse(Call<User> call, Response<User> response) {

                    if (response.isSuccessful()) {

                        userActive = response.body();

                        profile_image.setMaxWidth(70);

                        profile_image.setMaxHeight(70);

                        Picasso.with(MainActivity.this).load("http://www.gravatar.com/avatar/"

                                + userActive.avatar.gravatar.hash).into(profile_image);

//                        Picasso.with(MainActivity.this)
//                                .load(R.drawable.guest).fit()
//                                .into(profile_image);
//                        name3.setText("Guest");

                        Toast.makeText(MainActivity.this, "Uspesno Gravatar", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(MainActivity.this, "greska gravatar", Toast.LENGTH_SHORT).show();

                    }

                }

                @Override

                public void onFailure(Call<User> call, Throwable t) {

                }

            });

        }


        if (!token.isEmpty()) {

            name3.setText(name2);
            logout_login.setTitle("Logout");

            itemLog = "Logout";
        } else {
            Picasso.with(MainActivity.this)
                    .load(R.drawable.guest).fit()
                    .into(profile_image);
            name3.setText("Guest");

            logout_login.setTitle("Login");

            itemLog = "Login";

        }


    }


    void SearchListener() {

        search.addTextChangedListener(new TextWatcher() {

            @Override

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override

            public void onTextChanged(final CharSequence s, int start, int before, int count) {

                handler = new Handler();

                handler.postDelayed(new Runnable() {

                    @Override

                    public void run() {

                        Log.e("handler", s + "");

                        if (s.toString().isEmpty()) {

                            rv.setVisibility(View.GONE);

                            pager.setVisibility(View.VISIBLE);

                            tabLayout.setVisibility(View.VISIBLE);

                        } else {

                            String movie = search.getText().toString();

                            rv.setVisibility(View.VISIBLE);

                            pager.setVisibility(View.GONE);

                            tabLayout.setVisibility(View.GONE);

                            MovieSearch(movie);
                        }
                    }
                }, 1000);

            }

            @Override

            public void afterTextChanged(Editable s) {

            }

        });

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
        // getMenuInflater().inflate(R.menu.main, menu);
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
            Intent intent = new Intent(MainActivity.this, Favorites.class);
            startActivity(intent);

        } else if (id == R.id.rated) {
            Intent intent = new Intent(MainActivity.this, RatedActivity.class);
            startActivity(intent);
        } else if (id == R.id.watchlist) {
            Intent intent = new Intent(MainActivity.this, Watchlist.class);
            startActivity(intent);
        } else if (id == R.id.people) {
            Intent intent = new Intent(MainActivity.this, PeopleActivity.class);
            startActivity(intent);

        } else if (id == R.id.login) {

            if (itemLog.equals("Logout")) {

                PrefererencesManager2.removeToken(MainActivity.this);

                PrefererencesManager2.removeSessionID(MainActivity.this);

                Intent intent = new Intent(MainActivity.this, Login.class);

                startActivity(intent);

                finish();
            } else {


                Intent intent = new Intent(MainActivity.this, Login.class);

                startActivity(intent);

                finish();
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void MovieSearch(String link) {

        RestApi api = new RestApi(MainActivity.this);

        Call<MovieDataModel> call = api.getSearchMovie(link);

        call.enqueue(new Callback<MovieDataModel>() {

            @Override

            public void onResponse(Call<MovieDataModel> call, Response<MovieDataModel> response) {

                if (response.code() == 200) {

                    model = response.body();

                    RVadapter = new ExlorerRecyclerViewAdapter(MainActivity.this, model, new OnRowClickListener() {

                        @Override
                        public void OnRowClick(Movie film, int pozicija) {
                            Intent intent = new Intent(MainActivity.this, DetailsActiviti.class);

                            intent.putExtra("details", film.id);

                            startActivityForResult(intent, 1111);
                        }
                    });

                    RVadapter.setItems(model.results);

                    rv.setHasFixedSize(true);

                    rv.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));

                    rv.setAdapter(RVadapter);
                }
            }

            @Override

            public void onFailure(Call<MovieDataModel> call, Throwable t) {

            }
        });

    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == 1111) {

            String session_id = PrefererencesManager2.getSessionID(this);


            RestApi api = new RestApi(MainActivity.this);

            Call<MovieDataModel> call = api.getUserFavorites("account_id", session_id);

            call.enqueue(new Callback<MovieDataModel>() {

                @Override

                public void onResponse(Call<MovieDataModel> call, Response<MovieDataModel> response) {

                    if (response.code() == 200) {

                        model = response.body();

                        RVadapter = new ExlorerRecyclerViewAdapter(MainActivity.this, model, new OnRowClickListener() {


                            @Override
                            public void OnRowClick(Movie film, int pozicija) {
                                Intent intent = new Intent(MainActivity.this, DetailsActiviti.class);

                                intent.putExtra("details", film.id);

                                startActivity(intent);
                            }
                        });

                        RVadapter.setItems(model.results);

                        rv.setHasFixedSize(true);

                        rv.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));

                        rv.setAdapter(RVadapter);


                    }
                }

                @Override

                public void onFailure(Call<MovieDataModel> call, Throwable t) {

                }
            });
        }
    }


}
