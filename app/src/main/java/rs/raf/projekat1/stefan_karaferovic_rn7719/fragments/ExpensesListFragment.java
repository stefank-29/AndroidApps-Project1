package rs.raf.projekat1.stefan_karaferovic_rn7719.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import rs.raf.projekat1.stefan_karaferovic_rn7719.R;
import rs.raf.projekat1.stefan_karaferovic_rn7719.activities.DisplayFinanceActivity;
import rs.raf.projekat1.stefan_karaferovic_rn7719.activities.EditFinanceActivity;
import rs.raf.projekat1.stefan_karaferovic_rn7719.models.Finance;
import rs.raf.projekat1.stefan_karaferovic_rn7719.recycler.adapter.ExpensesAdapter;
import rs.raf.projekat1.stefan_karaferovic_rn7719.recycler.adapter.IncomesAdapter;
import rs.raf.projekat1.stefan_karaferovic_rn7719.recycler.differ.FinanceDiffItemCallback;
import rs.raf.projekat1.stefan_karaferovic_rn7719.viewmodels.BalanceViewModel;

public class ExpensesListFragment extends Fragment {

    // Codes
    public static final int EXPENSE_REQUEST_CODE = 1;
    public static final String EXPENSE_RECEIVED = "financeRecived";


    // View comps
    private RecyclerView recyclerView;

    private BalanceViewModel balanceViewModel;
    private ExpensesAdapter expensesAdapter;

    public ExpensesListFragment() {
        super(R.layout.fragment_expenses_list);
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
        initObservers(view);
        initRecycler(view);
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recyclerExpenses);
    }

    private void initObservers(View view) {
        balanceViewModel.getExpenses().observe(getViewLifecycleOwner(), expenses -> {
            expensesAdapter.submitList(expenses);
        });
    }

    private void initRecycler(View view) {
        expensesAdapter = new ExpensesAdapter(new FinanceDiffItemCallback(), finance -> {
            balanceViewModel.deleteExpense(finance.getId());
            return null;
        }, finance -> {
            // edit
            Intent intent = new Intent(getActivity(), EditFinanceActivity.class);
            intent.putExtra(EditFinanceActivity.FINANCE, finance);
            intent.putExtra(EditFinanceActivity.FINANCE_TYPE, "expense");
            startActivityForResult(intent, EXPENSE_REQUEST_CODE);
            return null;
        }, finance -> {
            Intent intent = new Intent(getActivity(), DisplayFinanceActivity.class);
            intent.putExtra(DisplayFinanceActivity.FINANCE, finance);
            startActivity(intent);
            return null;
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(expensesAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case EXPENSE_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Finance finance = (Finance) data.getExtras().getSerializable(EXPENSE_RECEIVED);
                    balanceViewModel.editExpense(finance);
                }
                break;

        }
    }

}
