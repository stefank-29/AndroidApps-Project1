package rs.raf.projekat1.stefan_karaferovic_rn7719.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import rs.raf.projekat1.stefan_karaferovic_rn7719.R;
import rs.raf.projekat1.stefan_karaferovic_rn7719.recycler.adapter.ExpensesAdapter;
import rs.raf.projekat1.stefan_karaferovic_rn7719.recycler.adapter.IncomesAdapter;
import rs.raf.projekat1.stefan_karaferovic_rn7719.recycler.differ.FinanceDiffItemCallback;
import rs.raf.projekat1.stefan_karaferovic_rn7719.viewmodels.BalanceViewModel;

public class ExpensesListFragment extends Fragment {

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
        balanceViewModel = new ViewModelProvider(this).get(BalanceViewModel.class);
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
            // na klik brisanje ili detaljan prikaz
            return null;
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(expensesAdapter);
    }


}
