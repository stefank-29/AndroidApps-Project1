package rs.raf.projekat1.stefan_karaferovic_rn7719.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rs.raf.projekat1.stefan_karaferovic_rn7719.R;
import rs.raf.projekat1.stefan_karaferovic_rn7719.viewmodels.BalanceViewModel;
import rs.raf.projekat1.stefan_karaferovic_rn7719.viewmodels.InputViewModel;

public class InputFragment extends Fragment {

    private final String DESC_FRAGMENT_TAG = "descFragment";
    private final String AUDIO_FRAGMENT_TAG = "audioFragment";

    // View comps
    private Spinner spinner;
    private EditText titleEt;
    private EditText amountEt;

    private CheckBox checkBox;
    private Button submitBtn;

    private InputViewModel inputViewModel;
    private BalanceViewModel balanceViewModel;

    // Permission
    private final int PERMISSION_ALL = 1;
    private final String[] PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public InputFragment() {
        super(R.layout.fragment_input);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        balanceViewModel = new ViewModelProvider(requireActivity()).get(BalanceViewModel.class);
        inputViewModel = new ViewModelProvider(requireActivity()).get(InputViewModel.class);
        init(view);
    }

    private void init(View view) {
        initView(view);
        initFragment();
        initSpinner();
        initListeners(view);
    }

    private void initView(View view) {
        titleEt = view.findViewById(R.id.titleEt);
        amountEt = view.findViewById(R.id.amountEt);
        checkBox = view.findViewById(R.id.inputCb);
        spinner = view.findViewById(R.id.inputSpinner);
        submitBtn = view.findViewById(R.id.submitBtn);
    }

    private void initSpinner() {
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(getContext(), R.array.finance_types, android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

    }

    private void initFragment() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.inputFc, new DescriptionFragment(), DESC_FRAGMENT_TAG);
        transaction.commit();
    }

    private void initListeners(View aview) {
        submitBtn.setOnClickListener(v -> {
            boolean valid = true;
            String title = "";
            int amount = 0;
            String desc = "";
            File file = null;
            String type = spinner.getSelectedItem().toString();

            if (titleEt.getText().toString().equals("")) {
                valid = false;
                Toast.makeText(getContext(), "Naslov je obavezan", Toast.LENGTH_SHORT).show();
            } else {
                title = titleEt.getText().toString();
            }

            if (amountEt.getText().toString().equals("")) {
                valid = false;
                Toast.makeText(getContext(), "Kolicina je obavezna", Toast.LENGTH_SHORT).show();
            } else {
                amount = Integer.parseInt(amountEt.getText().toString());

            }


            // provera opisa u zavisnosti od checkbox-a
            if (checkBox.isChecked()) {
                if (inputViewModel.getAudio().getValue().length() == 0) {
                    valid = false;
                    Toast.makeText(getContext(), "Opis je obavezan", Toast.LENGTH_SHORT).show();
                } else {
                    file = inputViewModel.getAudio().getValue();
                }
            } else {
                if (inputViewModel.getDesciption().getValue().equals("")) {
                    valid = false;
                    Toast.makeText(getContext(), "Opis je obavezan", Toast.LENGTH_SHORT).show();
                } else {
                    desc = inputViewModel.getDesciption().getValue();
                }
            }


            if (!valid) {
                return;
            }

            // TODO resetovati inpute
            if (checkBox.isChecked()) {
                if (type.equals("Prihod")) {
                    balanceViewModel.addIncome(title, amount, file);
                } else if (type.equals("Rashod")) {
                    balanceViewModel.addExpense(title, amount, file);
                }

            } else {
                if (type.equals("Prihod")) {
                    balanceViewModel.addIncome(title, amount, desc);
                } else if (type.equals("Rashod")) {
                    balanceViewModel.addExpense(title, amount, desc);
                }
                // Reset desc
                Fragment fragment = null;
                fragment = getChildFragmentManager().findFragmentByTag(DESC_FRAGMENT_TAG);
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

                transaction.remove(fragment);
                transaction.add(R.id.inputFc, new DescriptionFragment(), DESC_FRAGMENT_TAG);
                transaction.commit();

            }

            titleEt.setText("");
            amountEt.setText("");
            inputViewModel.storeDescription("");
            inputViewModel.storeAudio(new File("record.3pg"));

        });

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            FragmentTransaction transaction = createTransactionWithAnimation();
            if (isChecked) {
                if (hasPermissions(getContext(), PERMISSIONS)) {
                    transaction.replace(R.id.inputFc, new AudioFragment(), AUDIO_FRAGMENT_TAG);
                } else {
                    requestPermissions(PERMISSIONS, PERMISSION_ALL);
                }

            } else {
                transaction.replace(R.id.inputFc, new DescriptionFragment(), DESC_FRAGMENT_TAG);
            }
            transaction.commit();
        });
    }

    private FragmentTransaction createTransactionWithAnimation() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        // Dodajemo animaciju kada se fragment doda
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        // Dodajemo transakciju na backstack kako bi se pritisokm na back transakcija rollback-ovala
        transaction.addToBackStack(null);
        return transaction;
    }

    ////////////////////////////////////////////////////////////////////////////
    private boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (requestCode == PERMISSION_ALL) {
            if (grantResults.length > 0) {
                StringBuilder permissionsDenied = new StringBuilder();
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        permissionsDenied.append("\n").append(permissions[i]);
                    }
                }
                // nijedna nije odbijena
                if (permissionsDenied.toString().length() == 0) {
                    transaction.replace(R.id.inputFc, new AudioFragment());
                    transaction.commit();
                } else {
                    Toast.makeText(getContext(), "Missing permissions!" + permissionsDenied.toString(), Toast.LENGTH_SHORT).show();
                    checkBox.setChecked(false);

                }
            }
        }
    }
}
