package com.denshiksmile.android.travelup.users;

import android.content.Intent;
import android.widget.Toast;

import com.denshiksmile.android.travelup.activities.LoginActivity;
import com.denshiksmile.android.travelup.activities.UIActivity;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.core.Context;

import java.util.Map;

/**
 * Created by Denys Smile on 5/6/2016.
 */
public class EmailPasswordUser {

    private String userName;
    private String password;
    private String email;
    private android.content.Context context;

    public boolean isAccessLogin = false;

    public EmailPasswordUser(){}
    public EmailPasswordUser(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public android.content.Context getContext() {
        return context;
    }

    public void setContext(android.content.Context context) {
        this.context = context;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void autorizateAccount(Firebase firebase) {
        firebase.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                //System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
                isAccessLogin = true;
            }
            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                // there was an error
                isAccessLogin = false;
            }
        });
    }

    public void createAccount(Firebase firebase) {
        firebase.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                System.err.println("Successfully created user account with uid: " + result.get("uid"));
//                Toast.makeText(context, ("Successfully created user account with uid: " + result.get("uid")), Toast.LENGTH_SHORT);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                // there was an error
//                Toast.makeText(context, "Error on creating account!", Toast.LENGTH_SHORT);
                System.err.println("fuuuuuuuuuk");

            }
        });
    }

    public void removeAccount(Firebase firebase) {
        firebase.removeUser(email, password, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                // user removed
            }
            @Override
            public void onError(FirebaseError firebaseError) {
                // error encountered
            }
        });
    }
}
