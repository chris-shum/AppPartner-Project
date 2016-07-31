package com.apppartner.androidprogrammertest;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.DragEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class AnimationActivity extends ActionBarActivity {

    private Toolbar mToolbar;
    private TextView mToolbarTextView, mAnimationPromptTextView, mBonusPromptTextView;
    private ImageView mAppIcon, mHiddenAppIcon, mBackgroundImage;
    private Button mFadeButton;
    private android.widget.RelativeLayout.LayoutParams mLayoutParams;
    private RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

//        inserted toolbar
//        inserted text fonts
//        removed onBackPress as it creates an infinite backpress loop

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setBackgroundColor(getResources().getColor(android.R.color.black));
        mToolbarTextView = (TextView) findViewById(R.id.toolBarTextView);
        mToolbarTextView.setText(R.string.title_activity_animation);
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/Jelloween - Machinato ExtraLight.ttf");
        mToolbarTextView.setTypeface(myTypeface);
        Drawable backArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        backArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        mToolbar.setNavigationIcon(backArrow);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Typeface bonus = Typeface.createFromAsset(getAssets(), "fonts/Jelloween - Machinato SemiBold Italic.ttf");
        mAnimationPromptTextView = (TextView) findViewById(R.id.animationTextView);
        mAnimationPromptTextView.setTypeface(myTypeface);
        mBonusPromptTextView = (TextView) findViewById(R.id.bonusPromptTextView);
        mBonusPromptTextView.setTypeface(bonus);

        mAppIcon = (ImageView) findViewById(R.id.appIcon);
        mHiddenAppIcon = (ImageView) findViewById(R.id.hiddenAppIcon);
        mBackgroundImage = (ImageView) findViewById(R.id.animationImage);

//        animation code
//        for some reason the fade animation didn't work properly in this code so I had to do it this way
        mFadeButton = (Button) findViewById(R.id.fadeButton);
        mFadeButton.setOnClickListener(new View.OnClickListener() {
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
                        mAppIcon.startAnimation(fadeIn);
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                mAppIcon.startAnimation(fadeOut);
            }
        });

//        drag and drop code
        mAppIcon.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                ClipData dragData = new ClipData("AppIcon", mimeTypes, item);
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(mHiddenAppIcon);
                v.startDrag(dragData, myShadow, null, 0);
                return true;
            }
        });

        mBackgroundImage.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                mLayoutParams = (RelativeLayout.LayoutParams) mAppIcon.getLayoutParams();
                if (event.getAction() == DragEvent.ACTION_DRAG_LOCATION) {
                    int x_cord = (int) event.getX();
                    int y_cord = (int) event.getY();
                    mLayoutParams.leftMargin = x_cord;
                    mLayoutParams.topMargin = y_cord;
                    mAppIcon.setLayoutParams(mLayoutParams);
                }
                return true;
            }
        });
    }

//    used to help determine appIcon location
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        mLayoutParams = (RelativeLayout.LayoutParams) mAppIcon.getLayoutParams();
        mLayoutParams.leftMargin = mRelativeLayout.getWidth() / 3;
        mLayoutParams.topMargin = mRelativeLayout.getHeight() / 2 + 100;
        mAppIcon.setLayoutParams(mLayoutParams);
    }
}

