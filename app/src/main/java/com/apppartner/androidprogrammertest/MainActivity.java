package com.apppartner.androidprogrammertest;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
    private TextView mMainText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        inserted text fonts
        mMainText = (TextView) findViewById(R.id.mainActivityTextView);
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/Jelloween - Machinato Bold.ttf");
        mMainText.setTypeface(myTypeface);
    }

    public void onLoginButtonClicked(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void onChatButtonClicked(View v) {
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }

    public void onAnimationTestButtonClicked(View v) {
        Intent intent = new Intent(this, AnimationActivity.class);
        startActivity(intent);
    }

}
