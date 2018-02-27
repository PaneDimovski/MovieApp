package com.movieapp.anti.movieapp.Activitys;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.movieapp.anti.movieapp.Adapters.PeopleRecyclerViewAdapter;
import com.movieapp.anti.movieapp.Api.ApiService;
import com.movieapp.anti.movieapp.Api.RestApi;
import com.movieapp.anti.movieapp.MainActivity;
import com.movieapp.anti.movieapp.Models.PeopleDataModel;
import com.movieapp.anti.movieapp.Models.Token;
import com.movieapp.anti.movieapp.Models.User;
import com.movieapp.anti.movieapp.Others.PrefererencesManager;
import com.movieapp.anti.movieapp.Preferences.PrefererencesManager2;
import com.movieapp.anti.movieapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    ApiService service;
    RestApi api3;
    RestApi api4;
    RestApi api5;

    public User data;
    public Token tokenModel;
    public String token;

    Context context;
    public String sesion;

    @BindView(R.id.password)
    EditText pass;
    @BindView(R.id.username)
    EditText username;

    @BindView(R.id.kopce)
    Button kopce;
    String   token2;
    public PrefererencesManager2 manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);


    }

    @OnClick(R.id.kopce)
    public void Klik(View v) {
        getToken();

    }


    public void getToken() {


        api3 = new RestApi(Login.this);
        api3.checkInternet(new Runnable() {
            @Override
            public void run() {
                Call<Token> call = api3.getToken("request_token");

                call.enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        if (response.code() == 200) {

//get session

                            tokenModel = response.body();
                            token = tokenModel.request_token;


                            getLogin();  // text polinja


                        } else if (response.code() == 401) {

                            Toast.makeText(Login.this, "Greska na konekcija", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        Toast.makeText(Login.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });


    }


//getsession(){
    //onComlepte GET create session with login
    // sesion ID da se zacuva
    // koga ke se vkluci main ili splash scren direktno da se zacuva userot vo sharedPreferences
//}


//createSessionwithLogin(){
    //onComlepte  tokenModel=response.body();
    //PrefererencesManager2.addToken(tokenModel);
//}

    public void getLogin() {   // tuka se vnesuvaat stringovi za username pass

        final User data = new User(username.getText().toString(), pass.getText().toString());
        api4 = new RestApi(Login.this);
        api4.checkInternet(new Runnable() {
            @Override
            public void run() {
                Call<Token> call = api4.GetLogin(data.username, data.password, token);

                call.enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        if (response.code() == 200) {

                            tokenModel = response.body();
                            token2 = tokenModel.request_token;
                            PrefererencesManager2.addUserID(token2, Login.this);
                            getSesion();


                            //getsession(){
                            //onComlepte GET create session with login
                            // sesion ID da se zacuva
                            // koga ke se vkluci main ili splash scren direktno da se zacuva userot vo sharedPreferences


//createSessionwithLogin(){
                            //onComlepte  tokenModel=response.body();
                            //PrefererencesManager2.addToken(tokenModel);
//}


                        } else if (response.code() == 401) {

                            Toast.makeText(Login.this, "Greska na konekcija", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        Toast.makeText(Login.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });


    }


    public void getSesion() {


        api5 = new RestApi(Login.this);
        api5.checkInternet(new Runnable() {
            @Override
            public void run() {
                Call<Token> call = api5.getSesionId(token2);

                call.enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        if (response.code() == 200) {


                            tokenModel = response.body();
                            sesion = tokenModel.session_id;

                            PrefererencesManager2.addSessionID(sesion, Login.this);


                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);


                        } else if (response.code() == 401) {

                            Toast.makeText(Login.this, "Greska na konekcija", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        Toast.makeText(Login.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });


    }


}

