package com.adurcup.adurcupseller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;
import com.adurcup.adurcupseller.R;
import com.adurcup.adurcupseller.fragment.IntroFragment;

/**
 * Created by kshivang on 16/12/16.
 *
 */

public class BriefIntro extends AppIntro {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(IntroFragment.newInstance(R.layout.fragment_slide1));
        addSlide(IntroFragment.newInstance(R.layout.fragment_slide2));
        addSlide(IntroFragment.newInstance(R.layout.fragment_slide3));
        addSlide(IntroFragment.newInstance(R.layout.fragment_slide4));
        setFlowAnimation();

        // Override bar/separator color.
        setSeparatorColor(ContextCompat.getColor(BriefIntro.this, android.R.color.transparent));
        setNextArrowColor(ContextCompat.getColor(BriefIntro.this, R.color.colorPrimaryDark));
        setColorSkipButton(ContextCompat.getColor(BriefIntro.this, R.color.colorPrimaryDark));
        setColorDoneText(ContextCompat.getColor(BriefIntro.this, R.color.colorPrimaryDark));
        setIndicatorColor(ContextCompat.getColor(BriefIntro.this, R.color.colorPrimaryDark),
        ContextCompat.getColor(BriefIntro.this, R.color.colorAccentLight));
        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);
    }

    private void loadMainActivity() {
        Toast.makeText(this, getString(R.string.welcome),
                Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
        loadMainActivity();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        loadMainActivity();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}
