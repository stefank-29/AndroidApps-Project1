package rs.raf.projekat1.stefan_karaferovic_rn7719.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import rs.raf.projekat1.stefan_karaferovic_rn7719.R;
import rs.raf.projekat1.stefan_karaferovic_rn7719.activities.EditUserActivity;
import rs.raf.projekat1.stefan_karaferovic_rn7719.activities.LoginActivity;
import rs.raf.projekat1.stefan_karaferovic_rn7719.models.Finance;

public class AccountFragment extends Fragment {


    public static final int USER_REQUEST_CODE = 3;
    public static final String NAME_RECIVED = "nameRecived";
    public static final String SURNAME_RECIVED = "surnameRecived";
    public static final String BANK_RECIVED = "bank";


    // View comps
    private TextView nameTv;
    private TextView surnameTv;
    private TextView bankTv;
    private Button btnEdit;
    private Button btnLogout;

    private String name;
    private String surname;
    private String bank;


    public AccountFragment() {
        super(R.layout.fragment_account);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        initView(view);
        initUser();
        initListeners();
    }

    private void initView(View view) {
        nameTv = view.findViewById(R.id.nameTv);
        surnameTv = view.findViewById(R.id.surnameTv);
        bankTv = view.findViewById(R.id.bankTv);
        btnEdit = view.findViewById(R.id.btnEdit);
        btnLogout = view.findViewById(R.id.btnLogout);

    }

    private void initUser() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
        name = sharedPreferences.getString(LoginActivity.LOGIN_NAME, null);
        surname = sharedPreferences.getString(LoginActivity.LOGIN_SURNAME, null);
        bank = sharedPreferences.getString(LoginActivity.LOGIN_BANK_NAME, null);
        nameTv.setText(name);
        surnameTv.setText(surname);
        bankTv.setText(bank);

    }

    private void initListeners() {
        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditUserActivity.class);
            intent.putExtra(EditUserActivity.NAME, name);
            intent.putExtra(EditUserActivity.SURNAME, surname);
            intent.putExtra(EditUserActivity.BANK_NAME, bank);
            startActivityForResult(intent, USER_REQUEST_CODE);

        });

        btnLogout.setOnClickListener(v -> {

        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case USER_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    name = data.getExtras().getString(NAME_RECIVED);
                    surname = data.getExtras().getString(SURNAME_RECIVED);
                    bank = data.getExtras().getString(BANK_RECIVED);
                    nameTv.setText(name);
                    surnameTv.setText(surname);
                    bankTv.setText(bank);
                }
                break;

        }
    }
}
