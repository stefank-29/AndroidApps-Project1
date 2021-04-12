package rs.raf.projekat1.stefan_karaferovic_rn7719.recycler.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.function.Function;

import rs.raf.projekat1.stefan_karaferovic_rn7719.R;
import rs.raf.projekat1.stefan_karaferovic_rn7719.models.Finance;

public class ExpensesAdapter extends ListAdapter<Finance, ExpensesAdapter.ViewHolder> {

    private Function<Finance, Void> onFinanceClicked;


    public ExpensesAdapter(@NonNull DiffUtil.ItemCallback<Finance> diffCallback, Function<Finance, Void> onFinanceClicked) {
        super(diffCallback);
        this.onFinanceClicked = onFinanceClicked;
    }

    @NonNull
    @Override
    public ExpensesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expenses_list_item, parent, false);
        return new ViewHolder(view, position -> {
            Finance finance = getItem(position);
            onFinanceClicked.apply(finance);
            return null;
        });
    }

    // bindovanje modela sa VH
    @Override
    public void onBindViewHolder(@NonNull ExpensesAdapter.ViewHolder holder, int position) {
        Finance finance = getItem(position);
        holder.bind(finance);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView, Function<Integer, Void> onItemClicked) {
            super(itemView);
            // listeneri na click
            itemView.findViewById(R.id.deleteBtn).setOnClickListener(v -> {
                onItemClicked.apply(getAdapterPosition());
            });
        }

        // bindovanje podataka iz modela
        public void bind(Finance finance) {
            ((TextView) itemView.findViewById(R.id.itemTitle)).setText(finance.getTitle());
            ((TextView) itemView.findViewById(R.id.itemAmount)).setText(String.valueOf(finance.getAmount()));
        }
    }
}
