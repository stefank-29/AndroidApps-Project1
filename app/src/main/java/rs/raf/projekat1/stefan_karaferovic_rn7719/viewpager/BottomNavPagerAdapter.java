package rs.raf.projekat1.stefan_karaferovic_rn7719.viewpager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import rs.raf.projekat1.stefan_karaferovic_rn7719.fragments.AccountFragment;
import rs.raf.projekat1.stefan_karaferovic_rn7719.fragments.BalanceFragment;
import rs.raf.projekat1.stefan_karaferovic_rn7719.fragments.InputFragment;
import rs.raf.projekat1.stefan_karaferovic_rn7719.fragments.ListsFragment;

public class BottomNavPagerAdapter extends FragmentPagerAdapter {

    // unistava se samo view fragmenta

    private final int ITEM_COUNT = 4;
    public static final int FRAGMENT_1 = 0;
    public static final int FRAGMENT_2 = 1;
    public static final int FRAGMENT_3 = 2;
    public static final int FRAGMENT_4 = 3;

    public BottomNavPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case FRAGMENT_1:
                fragment = new BalanceFragment();
                break;
            case FRAGMENT_2:
                fragment = new InputFragment();
                break;
            case FRAGMENT_3:
                fragment = new ListsFragment();
                break;
            default:
                fragment = new AccountFragment();
                break;

        }
        return fragment;
    }


    @Override
    public int getCount() {
        return ITEM_COUNT;
    }


}
