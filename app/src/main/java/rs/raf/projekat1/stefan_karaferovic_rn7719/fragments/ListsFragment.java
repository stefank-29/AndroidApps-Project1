package rs.raf.projekat1.stefan_karaferovic_rn7719.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import rs.raf.projekat1.stefan_karaferovic_rn7719.R;
import rs.raf.projekat1.stefan_karaferovic_rn7719.viewpager.ListsTabPagerAdapter;

public class ListsFragment extends Fragment {

    // View comps
    private ViewPager viewPager;
    private TabLayout tabLayout;

    public ListsFragment() {
        super(R.layout.fragment_lists);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view); // sigurni smo da je view za taj fragment kreiran (view vezan za fragment)
    }

    private void init(View view) {
        initView(view);
        initTabs(view);
    }

    private void initView(View view) {
        viewPager = view.findViewById(R.id.listsViewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
    }

    private void initTabs(View view) {
        viewPager.setAdapter(new ListsTabPagerAdapter(getFragmentManager())); // jer trazi fm
        tabLayout.setupWithViewPager(viewPager); // vezem sa viewPager-om
    }
}
