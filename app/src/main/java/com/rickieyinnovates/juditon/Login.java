package com.rickieyinnovates.juditon;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Login extends AppCompatActivity {

    EditText usernameEdit, passwordEdit;
    private static final String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


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

            goToLogin(username, password);
        });

    }

    private void goToLogin(String username, String password) {
        ApiClient apiRequest = new ApiClient(this);

        apiRequest.login(username, password, "/api/auth/signin", new ApiClient.LoginCallback() {
            @Override
            public void onLoginSuccess(String authToken) {
                Intent toDashboardIntent = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(toDashboardIntent);
            }

            @Override
            public void onLoginError(String errorMessage) {
                Log.e(TAG, errorMessage);
                Toast.makeText(Login.this, errorMessage, Toast.LENGTH_SHORT).show();
                // Handle login error, e.g., display an error message to the user
            }
        });
    }

}