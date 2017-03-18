package com.adurcup.adurcupseller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.adurcup.adurcupseller.R;
import com.adurcup.adurcupseller.misc.UserLocalDatabase;

/**
 * Created by kshivang on 16/12/16.
 *
 */

public class SplashActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        Animation bounceAnim = AnimationUtils
                .loadAnimation(getApplicationContext(), R.anim.splash_logo);
        findViewById(R.id.logo).setAnimation(bounceAnim);

        bounceAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animationsFinished();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    boolean isAnimationFinished = false;

    public void animationsFinished() {
        isAnimationFinished = true;
        UserLocalDatabase localDatabase = new UserLocalDatabase(this);
        startActivity(new Intent(SplashActivity.this, localDatabase.isFirstRun()?
                BriefIntro.class : (localDatabase.isLoggedIn()?
                BusinessUpdateStatusActivity.class : (localDatabase.isWaitingOTP()?
                OtpActivity.class : LoginActivity.class))));
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if(isAnimationFinished) {
//            finish();
//        }
//    }
}
