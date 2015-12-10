package com.stoyanov.developer.complexcalculator.controller.controller;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;

import com.stoyanov.developer.complexcalculator.R;
import com.stoyanov.developer.complexcalculator.controller.view.RevealBackgroundView;

public class AboutActivity extends AppCompatActivity implements RevealBackgroundView.OnStateChangeListener {

    private static final String TAG = "AboutActivity";
    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";
    private RevealBackgroundView vRevealBackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        vRevealBackground = (RevealBackgroundView) findViewById(R.id.vRevealBackground);
        setupRevealBackground(savedInstanceState);
    }

    public static void startUserProfileFromLocation(int[] startingLocation, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, AboutActivity.class);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        startingActivity.startActivity(intent);
    }

    private void setupRevealBackground(Bundle savedInstanceState) {
        vRevealBackground.setOnStateChangeListener(this);
        if (savedInstanceState == null) {
            final int[] startingLocation = getIntent().getIntArrayExtra(ARG_REVEAL_START_LOCATION);
            vRevealBackground.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    vRevealBackground.getViewTreeObserver().removeOnPreDrawListener(this);
                    vRevealBackground.startFromLocation(startingLocation);
                    return false;
                }
            });
        } else {
//            userPhotosAdapter.setLockedAnimations(true);
            vRevealBackground.setToFinishedFrame();
        }
    }

    @Override
    public void onStateChange(int state) {
        Log.i(TAG, "onStateChange: state - " + state);
    }
}
