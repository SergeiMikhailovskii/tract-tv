package com.mikhailovskii.trakttv.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.mikhailovskii.trakttv.R;
import com.mikhailovskii.trakttv.ui.main.MainActivity;

import org.json.JSONException;

import java.util.Collections;

public class LoginActivity extends AppCompatActivity
        implements LoginContract.LoginView {

    public static final String EXTRA_LOGIN = "EXTRA_LOGIN";
    public static final String EXTRA_PASSWORD = "EXTRA_PASSWORD";
    public static final String EXTRA_TOKEN = "EXTRA_TOKEN";
    public static final String EXTRA_EMAIL = "EXTRA_EMAIL";
    public static final String EXTRA_ID = "EXTRA_ID";

    private static final String FB_EMAIL_PERMISSION = "email";
    private static final String FB_ID_PERMISSION = "id";

    private EditText mLoginEdit;
    private EditText mPasswordEdit;
    private LoginButton mFacebookButton;
    private Button mLoginButton;

    private CallbackManager mCallbackManager;
    private LoginPresenter mPresenter = new LoginPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mPresenter.attachView(this);

        // Find views
        mLoginEdit = findViewById(R.id.login_edit);
        mPasswordEdit = findViewById(R.id.password_edit);
        mFacebookButton = findViewById(R.id.facebook_button);
        mLoginButton = findViewById(R.id.login);

        // Handle login button
        mLoginButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(EXTRA_LOGIN, mLoginEdit.getText().toString());
            bundle.putString(EXTRA_PASSWORD, mPasswordEdit.getText().toString());

            mPresenter.saveUserData(bundle);
        });

        // Facebook logic
        mCallbackManager = CallbackManager.Factory.create();
        mFacebookButton.setReadPermissions(Collections.singletonList(FB_EMAIL_PERMISSION));
        mFacebookButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                final String[] email = new String[1];
                final String[] id = new String[1];

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        (object, response) -> {
                            GraphResponse response1 = response;
                            try {
                                email[0] = object.getString(FB_EMAIL_PERMISSION);
                                id[0] = object.getString(FB_ID_PERMISSION);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            response.getRawResponse();
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,email");
                request.setParameters(parameters);
                request.executeAsync();

                Bundle bundle = new Bundle();
                bundle.putString(EXTRA_TOKEN, loginResult.getAccessToken().getToken());
                bundle.putString(EXTRA_EMAIL, email[0]);
                bundle.putString(EXTRA_ID, id[0]);

                mPresenter.saveUserData(bundle);
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.login_fb_cancelled), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.failed_fb_login), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onLoggedIn() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoginFailed() {
        Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void showEmptyState(@NonNull Boolean value) {

    }

    @Override
    public void showLoadingIndicator(@NonNull Boolean value) {

    }

}
