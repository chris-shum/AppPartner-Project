package com.apppartner.androidprogrammertest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.apppartner.androidprogrammertest.models.AppPartnerData;
import com.apppartner.androidprogrammertest.retrofit.PostRequest;

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
        Typeface editTextTypeface = Typeface.createFromAsset(getAssets(), "fonts/Jelloween - Machinato.ttf");
        username.setTypeface(editTextTypeface);
        password.setTypeface(editTextTypeface);
        loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginToAppPartner(view.getContext());
            }
        });
    }

    private void LoginToAppPartner(final Context context) {

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
                long elapsedTime = System.currentTimeMillis() - startTime;
                CreateDialog(context, response, elapsedTime);
            }

            @Override
            public void onFailure(Call<AppPartnerData> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Login failed.  Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }
        });

    }


//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//    }

    public void CreateDialog(Context context, final Response<AppPartnerData> response, long time) {
        new AlertDialog.Builder(context)
                .setTitle("App Partner Response")
                .setMessage("Code: " + response.body().getCode() + "\nMessage: " + response.body().getMessage() + "\nTime to complete call: " + time + "ms")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (response.body().getCode().equals("Success")) {

                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            dialog.dismiss();
                        }
                    }
                })
                .show();
    }

}
