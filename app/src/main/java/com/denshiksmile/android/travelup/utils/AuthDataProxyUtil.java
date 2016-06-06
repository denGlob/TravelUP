package com.denshiksmile.android.travelup.utils;

import com.firebase.client.AuthData;

/**
 * Created by Denys Smile on 5/11/2016.
 */
public class AuthDataProxyUtil {
    private AuthData authData;

    public void setAuthData(AuthData authData) {
        this.authData = authData;
    }

    public AuthData getAuthData() {
        return authData;
    }
}
