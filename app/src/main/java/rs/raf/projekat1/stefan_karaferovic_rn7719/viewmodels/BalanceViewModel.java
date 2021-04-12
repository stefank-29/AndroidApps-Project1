package rs.raf.projekat1.stefan_karaferovic_rn7719.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import rs.raf.projekat1.stefan_karaferovic_rn7719.models.Finance;

public class BalanceViewModel extends ViewModel {
    public static int counter = 21;

    private final MutableLiveData<List<Finance>> incomes = new MutableLiveData<>();
    private final ArrayList<Finance> incomesList = new ArrayList<>();

    private final MutableLiveData<List<Finance>> expenses = new MutableLiveData<>();
    private final ArrayList<Finance> expensesList = new ArrayList<>();

    public BalanceViewModel() {
        // incomes
        for (int i = 1; i <= 10; i++) {
            Finance finance = new Finance(i, "Uplata", 150, "Opis neki");
            incomesList.add(finance);
        }
        ArrayList<Finance> listToSubmit = new ArrayList<>(incomesList);
        incomes.setValue(listToSubmit);

        // expenses
        for (int i = 11; i <= 20; i++) {
            Finance finance = new Finance(i, "Isplata", 50, "Opis neki");
            expensesList.add(finance);
        }
        ArrayList<Finance> listToSubmit2 = new ArrayList<>(expensesList);
        expenses.setValue(listToSubmit2);
    }

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

    public int getDifference() {
        return getIncomesSum() - getExpensesSum();
    }

    public void deleteIncome(int id) {
        incomesList.removeIf(finance -> finance.getId() == id);
        ArrayList<Finance> listToSubmit = new ArrayList<>(incomesList);
        incomes.setValue(listToSubmit);
    }

    public void deleteExpense(int id) {
        expensesList.removeIf(finance -> finance.getId() == id);
        ArrayList<Finance> listToSubmit = new ArrayList<>(expensesList);
        expenses.setValue(listToSubmit);
    }


}
