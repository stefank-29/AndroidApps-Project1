package rs.raf.projekat1.stefan_karaferovic_rn7719.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import rs.raf.projekat1.stefan_karaferovic_rn7719.R;
import rs.raf.projekat1.stefan_karaferovic_rn7719.viewmodels.BalanceViewModel;
import rs.raf.projekat1.stefan_karaferovic_rn7719.viewmodels.InputViewModel;

public class DescriptionFragment extends Fragment {

    private InputViewModel inputViewModel;

    private EditText descInput;

    public DescriptionFragment() {
        super(R.layout.fragment_description);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inputViewModel = new ViewModelProvider(requireActivity()).get(InputViewModel.class);
        init(view);
    }

    private void init(View view) {
        initView(view);
        initListeners(view);
    }

    private void initView(View view) {
        descInput = view.findViewById(R.id.descInput);

    }

    private void initListeners(View view) {
        descInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inputViewModel.storeDescription(s.toString());
            }
        });
    }

}
