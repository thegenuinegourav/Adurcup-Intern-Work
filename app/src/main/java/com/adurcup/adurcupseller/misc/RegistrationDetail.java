package com.adurcup.adurcupseller.misc;

/**
 * Created by kshivang on 20/12/16.
 *
 */

public class RegistrationDetail {
    private String registrationKey;
    private String name;
    private String mobile;
    private String password;
    private Long requestTime;
    private Boolean forgotPassword;

    public String getRegistrationKey() {
        return registrationKey;
    }

    public void setRegistrationKey(String registrationKey) {
        this.registrationKey = registrationKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Long requestTime) {
        this.requestTime = requestTime;
    }

    public Boolean isForgotPassword() {
        return forgotPassword;
    }

    public void setForgotPassword(Boolean forgotPassword) {
        this.forgotPassword = forgotPassword;
    }
}
