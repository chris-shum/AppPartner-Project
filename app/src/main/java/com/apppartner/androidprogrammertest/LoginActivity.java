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

    private Toolbar mToolbar;
    private TextView mToolbarText;
    private EditText mUsernameEditText, mPasswordEditText;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        inserted toolbar
//        inserted text fonts
//        imported retrofit library to handle API post request
//        removed onBackPress as it creates an infinite backpress loop

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setBackgroundColor(getResources().getColor(android.R.color.black));
        mToolbarText = (TextView) findViewById(R.id.toolBarTextView);
        mToolbarText.setText(R.string.title_activity_login);
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/Jelloween - Machinato ExtraLight.ttf");
        mToolbarText.setTypeface(myTypeface);
        Drawable backArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        backArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        mToolbar.setNavigationIcon(backArrow);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mUsernameEditText = (EditText) findViewById(R.id.usernameEditText);
        mPasswordEditText = (EditText) findViewById(R.id.passwordEditText);
        Typeface editTextTypeface = Typeface.createFromAsset(getAssets(), "fonts/Jelloween - Machinato.ttf");
        mUsernameEditText.setTypeface(editTextTypeface);
        mPasswordEditText.setTypeface(editTextTypeface);
        mLoginButton = (Button) findViewById(R.id.loginButton);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginToAppPartner(view.getContext());
            }
        });
    }

    //retrofit API post request
    private void LoginToAppPartner(final Context context) {
        String rootURL = getResources().getString(R.string.root_URL);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(rootURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostRequest postRequest = retrofit.create(PostRequest.class);
        Call<AppPartnerData> call = postRequest.LoginToAppPartner(mUsernameEditText.getText().toString(), mPasswordEditText.getText().toString());
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
