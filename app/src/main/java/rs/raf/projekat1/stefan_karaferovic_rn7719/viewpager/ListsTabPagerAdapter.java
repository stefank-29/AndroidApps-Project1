package rs.raf.projekat1.stefan_karaferovic_rn7719.viewpager;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import rs.raf.projekat1.stefan_karaferovic_rn7719.fragments.AccountFragment;
import rs.raf.projekat1.stefan_karaferovic_rn7719.fragments.BalanceFragment;
import rs.raf.projekat1.stefan_karaferovic_rn7719.fragments.ExpensesListFragment;
import rs.raf.projekat1.stefan_karaferovic_rn7719.fragments.IncomesListFragment;
import rs.raf.projekat1.stefan_karaferovic_rn7719.fragments.InputFragment;
import rs.raf.projekat1.stefan_karaferovic_rn7719.fragments.ListsFragment;

public class ListsTabPagerAdapter extends FragmentPagerAdapter {


    private final int ITEM_COUNT = 2;
    public static final int FRAGMENT_1 = 0;
    public static final int FRAGMENT_2 = 1;


    public ListsTabPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case FRAGMENT_1:
                fragment = new IncomesListFragment();
                break;
            default:
                fragment = new ExpensesListFragment();
                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        // TODO: dodati za engleski prevod
        switch (position) {
            case FRAGMENT_1:
                return "PRIHODI";
            default:
                return "RASHODI";
        }
    }
}
