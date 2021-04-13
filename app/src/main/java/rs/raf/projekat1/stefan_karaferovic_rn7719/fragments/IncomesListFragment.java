package rs.raf.projekat1.stefan_karaferovic_rn7719.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

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
            intent.putExtra(EditFinanceActivity.FINANCE_ID, finance);
            startActivity(intent);
            return null;
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(financeAdapter);
    }
}
