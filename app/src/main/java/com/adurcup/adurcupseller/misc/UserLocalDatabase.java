package com.adurcup.adurcupseller.misc;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.adurcup.adurcupseller.BuildConfig;

public class UserLocalDatabase {

    private static final String KEY_FIRST_RUN = "f";
    private static final String KEY_USER_DETAIL = "u";
    private static final String KEY_API_KEY = "a";
    private static final String KEY_LOG_IN = "l";
    private static final String KEY_OTP_WAIT = "o";
    private static final String KEY_REGISTRATION_DETAIL = "r";

    private static SharedPreferences userLocalDatabase;

    public UserLocalDatabase(Context context) {
        userLocalDatabase = context
                .getSharedPreferences(BuildConfig.APPLICATION_ID,
                Context.MODE_PRIVATE);
    }

    public boolean isFirstRun() {
        if (userLocalDatabase.getBoolean(KEY_FIRST_RUN, true)) {
            SharedPreferences.Editor spEditor = userLocalDatabase.edit();
            spEditor.putBoolean(KEY_FIRST_RUN, false);
            spEditor.apply();
            return true;
        }
        return false;
    }

    public void login(UserDetail user) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        Gson gson = new Gson();
        String userDetail = gson.toJson(user, UserDetail.class);
        spEditor.putString(KEY_USER_DETAIL, userDetail);
        spEditor.putBoolean(KEY_LOG_IN, true);
        spEditor.apply();
    }

    public boolean isLoggedIn() {
        return userLocalDatabase.getBoolean(KEY_LOG_IN, false);
    }

    public void waitingOTP(RegistrationDetail registrationDetail) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        Gson gson = new Gson();
        String stRegistrationDetail = gson.toJson(registrationDetail, RegistrationDetail.class);
        spEditor.putString(KEY_REGISTRATION_DETAIL, stRegistrationDetail);
        spEditor.putBoolean(KEY_OTP_WAIT, true);
        spEditor.apply();
    }

    public boolean isWaitingOTP() {
        return userLocalDatabase.getBoolean(KEY_OTP_WAIT, false);
    }

    public RegistrationDetail getRegistrationDetail() {
        String stRegistration = userLocalDatabase.getString(KEY_REGISTRATION_DETAIL, "");
        Gson gson = new Gson();
        try {
            return gson.fromJson(stRegistration, RegistrationDetail.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void clearOTPRequest() {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.remove(KEY_REGISTRATION_DETAIL);
        spEditor.remove(KEY_OTP_WAIT);
        spEditor.apply();
    }

    public void logout() {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.remove(KEY_API_KEY);
        spEditor.remove(KEY_USER_DETAIL);
        spEditor.putBoolean(KEY_LOG_IN, false);
        spEditor.apply();
    }

    public UserDetail getUserDetail() {
        String userDetailString = userLocalDatabase.getString(KEY_USER_DETAIL, "");
        Gson gson = new Gson();
        try {
            return gson.fromJson(userDetailString, UserDetail.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
