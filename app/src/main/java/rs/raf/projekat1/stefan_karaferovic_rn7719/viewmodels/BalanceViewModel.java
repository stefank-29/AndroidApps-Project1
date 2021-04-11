package rs.raf.projekat1.stefan_karaferovic_rn7719.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import rs.raf.projekat1.stefan_karaferovic_rn7719.models.Finance;

public class BalanceViewModel extends ViewModel {
    private final MutableLiveData<List<Finance>> incomes = new MutableLiveData<>();
    private final ArrayList<Finance> incomesList = new ArrayList<>();

    private final MutableLiveData<List<Finance>> expenses = new MutableLiveData<>();
    private final ArrayList<Finance> expensesList = new ArrayList<>();

    // da ne moze da se menja
    public LiveData<List<Finance>> getIncomes() {
        return incomes;
    }

    public LiveData<List<Finance>> getExpenses() {
        return expenses;
    }

    public int getIncomesSum() {
        int sum = 0;
        for (Finance income : incomesList) {
            sum += income.getAmount();
        }
        return sum;
    }

    public int getExpensesSum() {
        int sum = 0;
        for (Finance expense : expensesList) {
            sum += expense.getAmount();
        }
        return sum;
    }
}
