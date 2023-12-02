package com.example.inz2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private EditText password_et, username_et;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.material_button);
        username_et = findViewById(R.id.username_et);
        password_et = findViewById(R.id.password_et);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        username_et.addTextChangedListener(loginTextWatcher);
        password_et.addTextChangedListener(loginTextWatcher);

        button.setEnabled(false);

        button.setOnClickListener(view -> {
            String username = username_et.getText().toString().trim();
            String password = password_et.getText().toString();
            loginViewModel.login(username, password);
        });

        loginViewModel.getLoginErrorLiveData().observe(this, loginError -> {
            if (loginError != null && loginError) {
                showLoginErrorDialog();
            }
        });

        loginViewModel.getLoginResponseLiveData().observe(this, loginResponse -> {
            if (loginResponse != null) {
                if(loginResponse.role.equals("Kierowca")){
                    String token = loginResponse.getToken();
                    saveTokenToSharedPreferences(token);
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                }else{
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Błąd dostępu")
                            .setMessage("Aplikacja przeznaczona tylko dla kierowców")
                            .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                            .show();
                }

            } else {
                Log.e("Logowanie", "Błąd logowania");
            }
        });
    }

    private void showLoginErrorDialog() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Błąd logowania")
                .setMessage("Błędne dane. Spróbuj ponownie.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            button.setEnabled(validateUserName() && validatePassword());
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean validateUserName() {
        String usernameText = username_et.getText().toString();
        if (usernameText.isEmpty()) {
            username_et.setError("Pole nie może być puste");
            return false;
        } else if (usernameText.length() > 10) {
            username_et.setError("Zbyt długa nazwa użytkownika");
            return false;
        } else {
            username_et.setError(null);
            return true;
        }
    }

    private void saveTokenToSharedPreferences(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("mySharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token_key", token);
        editor.apply();
    }

    private boolean validatePassword() {
        String passwordText = password_et.getText().toString();
        if (passwordText.isEmpty()) {
            password_et.setError("Pole nie może być puste");
            return false;
        } else {
            password_et.setError(null);
            return true;
        }
    }
}
