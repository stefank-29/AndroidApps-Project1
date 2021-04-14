package rs.raf.projekat1.stefan_karaferovic_rn7719.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.concurrent.ThreadLocalRandom;

import rs.raf.projekat1.stefan_karaferovic_rn7719.R;

public class LoginActivity extends AppCompatActivity {

    // Codes
    public static final String LOGIN_NAME = "name";
    public static final String LOGIN_PASSWORD = "password";
    public static final String LOGIN_SURNAME = "surname";
    public static final String LOGIN_BANK_NAME = "bankName";


    // Hardcoded user
//    public static final String USERNAME = "usertest";
    public static final String PASSWORD = "user123";

    // View comps
    private ImageView imageView;
    private EditText nameEt;
    private EditText surnameEt;
    private EditText bankNameEt;
    private EditText passwordEt;
    private Button loginButton;


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
        imageView = findViewById(R.id.loginIv);
        nameEt = findViewById(R.id.nameEt);
        surnameEt = findViewById(R.id.surnameEt);
        bankNameEt = findViewById(R.id.banknameEt);
        passwordEt = findViewById(R.id.passwordEt);
        loginButton = findViewById(R.id.loginBtn);
    }

    private void initGlide() {
        Glide.with(this).load("https://www.srbijadanas.com/sites/default/files/styles/full_article_image/public/16831/100-dinara_foto_wikimedia_0.jpg").into(imageView);
    }

    public void initListeners() {
        loginButton.setOnClickListener(v -> {
            String name = nameEt.getText().toString();
            String surname = surnameEt.getText().toString();
            String bankName = bankNameEt.getText().toString();
            String password = passwordEt.getText().toString();

            boolean valid = true;

            if (name.equals("")) {
                Toast.makeText(this, "Ime je obavezno", Toast.LENGTH_SHORT).show();
                valid = false;
            }

            if (surname.equals("")) {
                Toast.makeText(this, "Prezime je obavezno", Toast.LENGTH_SHORT).show();
                valid = false;

            }

            if (bankName.equals("")) {
                Toast.makeText(this, "Ime banke je obavezno", Toast.LENGTH_SHORT).show();
                valid = false;

            }

            if (password.equals("")) {
                Toast.makeText(this, "Sifra je obavezna", Toast.LENGTH_SHORT).show();
                valid = false;
            } else if (password.length() < 5) {
                Toast.makeText(this, "Sifra mora sadrzati bar 5 karaktera", Toast.LENGTH_SHORT).show();
                valid = false;
            } else if (!password.equals(LoginActivity.PASSWORD)) {
                Toast.makeText(this, "Uneta sifra nije ispravna", Toast.LENGTH_SHORT).show();
                valid = false;
            }

            if (!valid) {
                return;
            }


            // cuvanje podataka
            SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
            sharedPreferences
                    .edit()
                    .putString(LOGIN_NAME, name)
                    .apply();
            sharedPreferences
                    .edit()
                    .putString(LOGIN_SURNAME, surname)
                    .apply();
            sharedPreferences
                    .edit()
                    .putString(LOGIN_BANK_NAME, bankName)
                    .apply();
            sharedPreferences
                    .edit()
                    .putString(LOGIN_PASSWORD, password)
                    .apply();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        });

    }
}