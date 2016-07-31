package com.apppartner.androidprogrammertest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends ActionBarActivity {


    Toolbar toolbar;
    TextView toolbarText;
    EditText username, password;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(android.R.color.black));
        toolbarText = (TextView) findViewById(R.id.toolBarTextView);
        toolbarText.setText(R.string.title_activity_login);
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/Jelloween - Machinato ExtraLight.ttf");

        final Drawable backArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        backArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        toolbarText.setTypeface(myTypeface);
        toolbar.setNavigationIcon(backArrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        username = (EditText) findViewById(R.id.usernameEditText);
        password = (EditText) findViewById(R.id.passwordEditText);
        loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Yo")
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        });
                builder.create();

//                insertUser();
            }
        });

    }

    private void insertUser() {
        String rootURL = getResources().getString(R.string.root_URL);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(rootURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostRequest postRequest = retrofit.create(PostRequest.class);

        Call<AppPartnerData> call = postRequest.LoginToAppPartner(username.getText().toString(), password.getText().toString());

        call.enqueue(new Callback<AppPartnerData>() {
            long startTime = System.currentTimeMillis();

            @Override
            public void onResponse(Call<AppPartnerData> call, Response<AppPartnerData> response) {
                Log.d("Response", response.body().getCode() + "");
                Log.d("Response", response.body().getMessage() + "");
                Log.d("Response", response.headers() + "");
                Log.d("Response", response.message() + "");
                Log.d("Response", response.isSuccessful() + "");
                long elapsedTime = System.currentTimeMillis() - startTime;
                Log.d("Response", elapsedTime + "");

            }

            @Override
            public void onFailure(Call<AppPartnerData> call, Throwable t) {

            }
        });


    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public Dialog CreateDialog() {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
        builder.setMessage("Yo")
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });
        return builder.create();
    }

}
