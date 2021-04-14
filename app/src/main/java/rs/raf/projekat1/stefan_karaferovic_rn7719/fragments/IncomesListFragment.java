package rs.raf.projekat1.stefan_karaferovic_rn7719.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;

import rs.raf.projekat1.stefan_karaferovic_rn7719.R;
import rs.raf.projekat1.stefan_karaferovic_rn7719.activities.EditFinanceActivity;
import rs.raf.projekat1.stefan_karaferovic_rn7719.models.Finance;
import rs.raf.projekat1.stefan_karaferovic_rn7719.recycler.adapter.IncomesAdapter;
import rs.raf.projekat1.stefan_karaferovic_rn7719.recycler.differ.FinanceDiffItemCallback;
import rs.raf.projekat1.stefan_karaferovic_rn7719.viewmodels.BalanceViewModel;

public class IncomesListFragment extends Fragment implements Serializable {

    // Codes
    public static final int INCOME_REQUEST_CODE = 1;
    public static final String INCOME_RECEIVED = "financeRecived";

    // View comps
    private RecyclerView recyclerView;

    private BalanceViewModel balanceViewModel;
    private IncomesAdapter financeAdapter;

    public IncomesListFragment() {
        super(R.layout.fragment_incomes_list);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // instanciranje view modela
        balanceViewModel = new ViewModelProvider(requireActivity()).get(BalanceViewModel.class);
        init(view);
    }


    private void init(View view) {
        initView(view);
        initObservers();
        initRecycler();
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler);
    }

    private void initObservers() {
        balanceViewModel.getIncomes().observe(getViewLifecycleOwner(), incomes -> {
            financeAdapter.submitList(incomes);
        });
    }

    private void initRecycler() {
        financeAdapter = new IncomesAdapter(new FinanceDiffItemCallback(), finance -> {
            // delete
            balanceViewModel.deleteIncome(finance.getId());
            return null;
        }, finance -> {
            // edit
            Intent intent = new Intent(getActivity(), EditFinanceActivity.class);
            intent.putExtra(EditFinanceActivity.FINANCE, finance);
            intent.putExtra(EditFinanceActivity.FINANCE_TYPE, "income");
            startActivityForResult(intent, INCOME_REQUEST_CODE);
            return null;
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(financeAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case INCOME_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Finance finance = (Finance) data.getExtras().getSerializable(INCOME_RECEIVED);
                    balanceViewModel.editIncome(finance);
                }
                break;

        }
    }
}
