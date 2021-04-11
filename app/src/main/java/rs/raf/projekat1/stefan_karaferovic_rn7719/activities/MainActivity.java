package rs.raf.projekat1.stefan_karaferovic_rn7719.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import rs.raf.projekat1.stefan_karaferovic_rn7719.R;
import rs.raf.projekat1.stefan_karaferovic_rn7719.viewpager.BottomNavPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        initViewPager();
        initNavigation();

    }

    private void initViewPager() {
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new BottomNavPagerAdapter(getSupportFragmentManager()));
    }

    private void initNavigation() {
        ((BottomNavigationView) findViewById(R.id.bottomNavigation)).setOnNavigationItemSelectedListener(item -> {
            // izvucem id-ijeve iz xml-a
            // menjam aktivni fragment
            // okine se getItem iz PageAdaptera
            switch (item.getItemId()) {
                case R.id.navigation_1:
                    viewPager.setCurrentItem(BottomNavPagerAdapter.FRAGMENT_1, false);
                    break;
                case R.id.navigation_2:
                    viewPager.setCurrentItem(BottomNavPagerAdapter.FRAGMENT_2, false);
                    break;
                case R.id.navigation_3:
                    viewPager.setCurrentItem(BottomNavPagerAdapter.FRAGMENT_3, false);
                    break;
                case R.id.navigation_4:
                    viewPager.setCurrentItem(BottomNavPagerAdapter.FRAGMENT_4, false);
                    break;

            }

            return true;
        });
    }
}