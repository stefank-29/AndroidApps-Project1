package rs.raf.projekat1.stefan_karaferovic_rn7719.recycler.differ;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import rs.raf.projekat1.stefan_karaferovic_rn7719.models.Finance;

public class FinanceDiffItemCallback extends DiffUtil.ItemCallback<Finance> {
    @Override
    public boolean areItemsTheSame(@NonNull Finance oldItem, @NonNull Finance newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Finance oldItem, @NonNull Finance newItem) {
        return oldItem.getTitle().equals(newItem.getTitle())
                && oldItem.getAmount() == newItem.getAmount();
    }
}
