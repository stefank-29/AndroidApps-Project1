package rs.raf.projekat1.stefan_karaferovic_rn7719.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
//        String username = sharedPreferences.getString(LoginActivity.LOGIN_NAME, null);
        String password = sharedPreferences.getString(LoginActivity.LOGIN_PASSWORD, null);
        Intent intent;
        if (password == null || !password.equals(LoginActivity.PASSWORD)) {
            intent = new Intent(this, LoginActivity.class);

        } else {
            intent = new Intent(this, MainActivity.class);

        }
        startActivity(intent);
        finish();
    }
}