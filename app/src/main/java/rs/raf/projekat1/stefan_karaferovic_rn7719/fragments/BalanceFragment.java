package rs.raf.projekat1.stefan_karaferovic_rn7719.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import rs.raf.projekat1.stefan_karaferovic_rn7719.R;
import rs.raf.projekat1.stefan_karaferovic_rn7719.viewmodels.BalanceViewModel;

public class BalanceFragment extends Fragment {

    // View comps
    private TextView incomesSum;
    private TextView expensesSum;
    private TextView balanceDifference;

    // View model
    BalanceViewModel balanceViewModel;

    public BalanceFragment() {
        super(R.layout.fragment_balance);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        balanceViewModel = new ViewModelProvider(this).get(BalanceViewModel.class);
        init(view);
    }

    private void init(View view) {
        initView(view);
        initObservers(view);

    }

    private void initView(View view) {
        incomesSum = view.findViewById(R.id.balanceIncomeSum);
        expensesSum = view.findViewById(R.id.balanceExpensesSum);
        balanceDifference = view.findViewById(R.id.balanceDifference);

    }

    private void initObservers(View view) {
        balanceViewModel.getIncomes().observe(getViewLifecycleOwner(), incomes -> {
            int difference = balanceViewModel.getDifference();

            incomesSum.setText(String.valueOf(balanceViewModel.getIncomesSum()));
            balanceDifference.setText(String.valueOf(difference));

            if (difference >= 0) {
                balanceDifference.setTextColor(getResources().getColor(R.color.green));
            } else {
                balanceDifference.setTextColor(getResources().getColor(R.color.secondaryDarkColor));
            }
        });

        balanceViewModel.getExpenses().observe(getViewLifecycleOwner(), expenses -> {
            int difference = balanceViewModel.getDifference();

            expensesSum.setText(String.valueOf(balanceViewModel.getExpensesSum()));
            balanceDifference.setText(String.valueOf(balanceViewModel.getDifference()));

            if (difference >= 0) {
                balanceDifference.setTextColor(getResources().getColor(R.color.green));
            } else {
                balanceDifference.setTextColor(getResources().getColor(R.color.secondaryDarkColor));
            }
        });


    }
}
