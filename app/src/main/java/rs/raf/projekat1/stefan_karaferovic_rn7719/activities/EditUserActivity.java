package rs.raf.projekat1.stefan_karaferovic_rn7719.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import rs.raf.projekat1.stefan_karaferovic_rn7719.R;
import rs.raf.projekat1.stefan_karaferovic_rn7719.fragments.AccountFragment;
import rs.raf.projekat1.stefan_karaferovic_rn7719.fragments.ExpensesListFragment;
import rs.raf.projekat1.stefan_karaferovic_rn7719.fragments.IncomesListFragment;

public class EditUserActivity extends AppCompatActivity {

    // Codes
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String BANK_NAME = "bankName";

    private EditText editName;
    private EditText editSurname;
    private EditText editBank;
    private Button btnCancel;
    private Button btnSave;


    private String name;
    private String surname;
    private String bankName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        init();
    }

    private void init() {
        parseIntent();
        initView();
        initListeners();
    }

    private void parseIntent() {
        Intent intent = getIntent();
        name = intent.getExtras().getString(NAME);
        surname = intent.getExtras().getString(SURNAME);
        bankName = intent.getExtras().getString(BANK_NAME);

    }

    private void initView() {
        editName = findViewById(R.id.nameEdit);
        editName.setText(name);
        editSurname = findViewById(R.id.surnameEdit);
        editSurname.setText(surname);
        editBank = findViewById(R.id.bankEdit);
        editBank.setText(bankName);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);
    }

    private void initListeners() {
        btnCancel.setOnClickListener(v -> {
            finish();
        });

        btnSave.setOnClickListener(v -> {
            boolean valid = true;
            if (editName.getText().toString().equals("")) {
                valid = false;
                Toast.makeText(this, "Ime je obavezno", Toast.LENGTH_SHORT).show();
            } else {
                name = editName.getText().toString();
            }

            if (editSurname.getText().toString().equals("")) {
                valid = false;
                Toast.makeText(this, "Prezime je obavezno", Toast.LENGTH_SHORT).show();
            } else {
                surname = editSurname.getText().toString();
            }

            if (editBank.getText().toString().equals("")) {
                valid = false;
                Toast.makeText(this, "Ime banke je obavezno", Toast.LENGTH_SHORT).show();
            } else {
                bankName = editBank.getText().toString();
            }

            if (!valid) {
                return;
            }

            Intent returnIntent = new Intent();

            returnIntent.putExtra(AccountFragment.NAME_RECIVED, name);
            returnIntent.putExtra(AccountFragment.SURNAME_RECIVED, surname);
            returnIntent.putExtra(AccountFragment.BANK_RECIVED, bankName);
            saveToSharedPreferences();

            setResult(Activity.RESULT_OK, returnIntent);

            finish();


        });

    }

    private void saveToSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        sharedPreferences
                .edit()
                .putString(LoginActivity.LOGIN_NAME, name)
                .apply();
        sharedPreferences
                .edit()
                .putString(LoginActivity.LOGIN_SURNAME, surname)
                .apply();
        sharedPreferences
                .edit()
                .putString(LoginActivity.LOGIN_BANK_NAME, bankName)
                .apply();
    }
}