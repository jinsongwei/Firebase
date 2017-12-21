package com.williamjin.firebase.view.login;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;

/**
 * Created by william on 12/20/17.
 */

public class LoginAuthenticator {

    FirebaseAuth auth;
    LoginActivity activity;
    OnLoginInteraction onLoginInteraction;
    FirebaseUser user;

    public LoginAuthenticator(FirebaseAuth auth, LoginActivity activity) {
        this.auth = auth;
        this.activity = activity;
    }

    public void validateUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            user = auth.getCurrentUser();
                            onLoginInteraction.onUserValidation(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            onLoginInteraction.onUserValidation(user);
//                            Toast.makeText(LoginActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void createUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            user = auth.getCurrentUser();
                            onLoginInteraction.onUserCreation(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure" + task.getException().toString());
                            onLoginInteraction.onUserCreation(user);
                        }
                    }
                });
    }

    public void initInteractor(LoginPresenter loginPresenter) {
        this.onLoginInteraction = loginPresenter;
    }

    public void checkUser() {
        user = auth.getCurrentUser();
        if(user != null)
            onLoginInteraction.onSessionValid(true);
        else
            onLoginInteraction.onSessionValid(false);
    }

    interface OnLoginInteraction {

        void onUserCreation(FirebaseUser user);

        void onUserValidation(FirebaseUser user);

        void onSessionValid(boolean isValid);
    }
}
