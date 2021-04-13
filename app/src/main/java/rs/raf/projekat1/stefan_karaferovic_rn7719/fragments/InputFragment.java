package rs.raf.projekat1.stefan_karaferovic_rn7719.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import rs.raf.projekat1.stefan_karaferovic_rn7719.R;
import rs.raf.projekat1.stefan_karaferovic_rn7719.viewmodels.BalanceViewModel;
import rs.raf.projekat1.stefan_karaferovic_rn7719.viewmodels.InputViewModel;

public class InputFragment extends Fragment {

    // View comps
    private Spinner spinner;
    private EditText titleEt;
    private EditText amountEt;
    private CheckBox checkBox;
    private Button submitBtn;

    private InputViewModel inputViewModel;
    private BalanceViewModel balanceViewModel;


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
        initListeners();
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
        transaction.add(R.id.inputFc, new DescriptionFragment());
        transaction.commit();
    }

    private void initListeners() {
        submitBtn.setOnClickListener(v -> {
            boolean valid = true;
            String title = "";
            int amount = 0;
            String desc = "";
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

            if (inputViewModel.getDesciption().getValue().equals("")) {
                valid = false;
                Toast.makeText(getContext(), "Opis je obavezan", Toast.LENGTH_SHORT).show();
            } else {
                desc = inputViewModel.getDesciption().getValue();
            }

            if (!valid) {
                return;
            }

            if (type.equals("Prihod")) {
                balanceViewModel.addIncome(title, amount, desc);
            } else if (type.equals("Rashod")) {
                balanceViewModel.addExpense(title, amount, desc);
            }


            Toast.makeText(getContext(), spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
        });
    }
}
