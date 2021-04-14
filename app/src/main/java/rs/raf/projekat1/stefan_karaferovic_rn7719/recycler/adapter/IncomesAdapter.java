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

public class IncomesAdapter extends ListAdapter<Finance, IncomesAdapter.ViewHolder> {

    private Function<Finance, Void> onDeleteFinanceClicked;
    private Function<Finance, Void> onEditFinanceClicked;
    private Function<Finance, Void> onFinanceClicked;


    public IncomesAdapter(@NonNull DiffUtil.ItemCallback<Finance> diffCallback, Function<Finance, Void> onDeleteFinanceClicked, Function<Finance, Void> onEditFinanceClicked, Function<Finance, Void> onFinanceClicked) {
        super(diffCallback);
        this.onDeleteFinanceClicked = onDeleteFinanceClicked;
        this.onEditFinanceClicked = onEditFinanceClicked;
        this.onFinanceClicked = onFinanceClicked;
    }

    @NonNull
    @Override
    public IncomesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.incomes_list_item, parent, false);
        return new ViewHolder(view, position -> {
            Finance finance = getItem(position);
            onDeleteFinanceClicked.apply(finance);
            return null;
        }, position -> {
            Finance finance = getItem(position);
            onEditFinanceClicked.apply(finance);
            return null;
        }, position -> {
            Finance finance = getItem(position);
            onFinanceClicked.apply(finance);
            return null;
        });
    }

    // bindovanje modela sa VH
    @Override
    public void onBindViewHolder(@NonNull IncomesAdapter.ViewHolder holder, int position) {
        Finance finance = getItem(position);
        holder.bind(finance);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView, Function<Integer, Void> onDeleteClicked, Function<Integer, Void> onEditClicked, Function<Integer, Void> onItemClicked) {
            super(itemView);
            // listeneri na click
            itemView.findViewById(R.id.deleteBtn).setOnClickListener(v -> {
                onDeleteClicked.apply(getAdapterPosition());
            });

            itemView.findViewById(R.id.editBtn).setOnClickListener(v -> {
                onEditClicked.apply(getAdapterPosition());
            });

            itemView.setOnClickListener(v -> {
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
