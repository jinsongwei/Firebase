package com.williamjin.firebase.view.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseAuth;
import com.williamjin.firebase.R;
import com.williamjin.firebase.view.movie.MovieActivity;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private static final String TAG = "LoginActivity";
    private EditText etEmail;
    private EditText etPassword;
    private FirebaseAuth firebaseAuth;
    private LoginPresenter presenter;
    private String email;
    private String password;

    //facebook
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindViews();

        firebaseAuth = FirebaseAuth.getInstance();
        LoginAuthenticator loginAuthenticator = new LoginAuthenticator(firebaseAuth, this);

        // facebook login
        facebookLogin();

        presenter = new LoginPresenter(loginAuthenticator);
        presenter.attachView(this);
//        presenter.checkSession();
    }

    private void facebookLogin() {
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Toast.makeText(LoginActivity.this, "Facebook Login Success", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onSuccess: " + loginResult.getAccessToken().getUserId());
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.e(TAG, "onCancel: user canceled");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.e(TAG, "onError: " + exception.getMessage());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void bindViews() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
    }

    public void onFirebaseSignUp(View view) {
        getCredentials();
        presenter.createUser(email, password);
    }

    public void onFirebaseSignIn(View view) {
        getCredentials();
        presenter.validateUser(email, password);
    }

    private void getCredentials() {
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
    }


    @Override
    public void showError(String error) {

    }

    @Override
    public void onUserCreation(boolean isCreated) {
        Log.d(TAG, "onUserCreation: " + isCreated);
        if (isCreated) {
            showToast("Sign Up ");
        } else
            showToast("Sign Up Failed");
    }

    @Override
    public void onUserValidation(boolean isValid) {
        Log.d(TAG, "onUserValidation: " + isValid);
        if (isValid) {
            showToast("Signed in ");
        } else
            showToast("Sign in Failed");
    }

    @Override
    public void isSessionValid(boolean isValid) {
        if (isValid) {
            Intent intent = new Intent(getApplicationContext(), MovieActivity.class);
            startActivity(intent);
        }
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
