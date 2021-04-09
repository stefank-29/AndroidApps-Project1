package rs.raf.projekat1.stefan_karaferovic_rn7719.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import rs.raf.projekat1.stefan_karaferovic_rn7719.R;

public class LoginActivity extends AppCompatActivity {

    // Codes
    public static final String LOGIN_USERNAME = "username";
    public static final String LOGIN_PASSWORD = "password";

    // Hardcoded user
    public static final String username = "usertest";
    public static final String password = "user123";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        initView();
        initGlide();
        initListeners();
    }

    private void initView() {

    }

    private void initGlide() {

    }

    private void initListeners() {

    }
}