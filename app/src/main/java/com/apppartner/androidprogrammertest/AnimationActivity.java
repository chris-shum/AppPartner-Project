package com.apppartner.androidprogrammertest;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class AnimationActivity extends ActionBarActivity
{

    Toolbar toolbar;
    TextView toolbarText, animationPrompt, bonusPrompt;
    ImageView appIcon;
    Button fade;
    private android.widget.RelativeLayout.LayoutParams layoutParams;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(android.R.color.black));
        toolbarText = (TextView) findViewById(R.id.toolBarTextView);
        toolbarText.setText(R.string.title_activity_animation);
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

        Typeface bonus = Typeface.createFromAsset(getAssets(), "fonts/Jelloween - Machinato SemiBold Italic.ttf");
        animationPrompt = (TextView) findViewById(R.id.animationTextView);
        animationPrompt.setTypeface(myTypeface);
        bonusPrompt = (TextView) findViewById(R.id.bonusPrompt);
        bonusPrompt.setTypeface(bonus);

        appIcon = (ImageView) findViewById(R.id.appIcon);

        fade = (Button) findViewById(R.id.fadeButton);
        fade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation fadeOut = AnimationUtils.loadAnimation(view.getContext(), R.anim.fade_out);
                final Animation fadeIn = AnimationUtils.loadAnimation(view.getContext(), R.anim.fade_in);

                fadeOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        appIcon.startAnimation(fadeIn);
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                appIcon.startAnimation(fadeOut);

            }
        });


        appIcon.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData.Item item = new ClipData.Item((CharSequence)v.getTag());
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

                ClipData dragData = new ClipData(v.getTag().toString(),mimeTypes, item);
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(appIcon);

                v.startDrag(dragData,myShadow,null,0);
                return true;
            }
        });

        appIcon.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch(event.getAction())
                {
                    case DragEvent.ACTION_DRAG_STARTED:
                        layoutParams = (RelativeLayout.LayoutParams)v.getLayoutParams();
                        Log.d("test234", "Action is DragEvent.ACTION_DRAG_STARTED");

                        // Do nothing
                        break;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.d("test234", "Action is DragEvent.ACTION_DRAG_ENTERED");
                        int x_cord = (int) event.getX();
                        int y_cord = (int) event.getY();
                        Log.d("test234", "x:"+x_cord+" and y: "+y_cord);

                        break;

                    case DragEvent.ACTION_DRAG_EXITED :
                        Log.d("test234", "Action is DragEvent.ACTION_DRAG_EXITED");
                        x_cord = (int) event.getX();
                        y_cord = (int) event.getY();
                        layoutParams.leftMargin = x_cord;
                        layoutParams.topMargin = y_cord;
                        v.setLayoutParams(layoutParams);
                        Log.d("test234", "x:"+x_cord+" and y: "+y_cord);
                        break;

                    case DragEvent.ACTION_DRAG_LOCATION  :
                        Log.d("test234", "Action is DragEvent.ACTION_DRAG_LOCATION");
                        x_cord = (int) event.getX();
                        y_cord = (int) event.getY();
                        Log.d("test234", "x:"+x_cord+" and y: "+y_cord);

                        break;

                    case DragEvent.ACTION_DRAG_ENDED   :
                        Log.d("test234", "Action is DragEvent.ACTION_DRAG_ENDED");

                        // Do nothing
                        break;

                    case DragEvent.ACTION_DROP:
                        Log.d("test234", "ACTION_DROP event");

                        // Do nothing
                        break;
                    default: break;
                }
                return true;
            }
        });

        appIcon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(appIcon);

                    appIcon.startDrag(data, shadowBuilder, appIcon, 0);
                    appIcon.setVisibility(View.INVISIBLE);
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });



    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
