package com.rickieyinnovates.juditon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;

public class Login extends AppCompatActivity {

    EditText usernameEdit, passwordEdit;
    private static final String TAG = "Login";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait ...");
        usernameEdit = findViewById(R.id.username);
        passwordEdit = findViewById(R.id.password);

        Button login = findViewById(R.id.login);

        login.setOnClickListener(view -> {
            String username, password;
            username = usernameEdit.getText().toString();
            password = passwordEdit.getText().toString();

            if (TextUtils.isEmpty(username)) {
                usernameEdit.setError("Username cannot be empty");
                usernameEdit.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                passwordEdit.setError("Password cannot be empty");
                passwordEdit.requestFocus();
                return;
            }

            goToLogin(view, username, password);
        });

    }

    private void goToLogin(View view, String username, String password) {
        progressDialog.show();
        ApiClient apiRequest = new ApiClient(this);

        apiRequest.login(username, password, "/api/auth/signin", new ApiClient.LoginCallback() {
            @Override
            public void onLoginSuccess(String authToken) {
                progressDialog.dismiss();
                Log.i(TAG, "onLoginSuccess: token: " + authToken);

                Snackbar.make(view, "Login successful", Snackbar.LENGTH_SHORT).show();

                Intent toDashboardIntent = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(toDashboardIntent);
            }

            @Override
            public void onLoginError(String errorMessage) {
                progressDialog.dismiss();
                errorMessage = errorMessage != null ? errorMessage : "error";
                Log.e(TAG, errorMessage);
                Snackbar.make(view, errorMessage, Snackbar.LENGTH_LONG).show();
            }
        });
    }

}