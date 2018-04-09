package ifrn.tads.ddm.minisuap;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import ifrn.tads.ddm.minisuap.fragments.AboutFragment;
import ifrn.tads.ddm.minisuap.fragments.HomeFragment;
import ifrn.tads.ddm.minisuap.fragments.NewsFragment;

public class MainActivity extends FragmentActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int menuItemId = item.getItemId();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            if (menuItemId == R.id.navigation_home) {
                HomeFragment homeFragment = new HomeFragment();
                fragmentTransaction.replace(R.id.fragment_layout, homeFragment, "home");
            } else if (menuItemId == R.id.navigation_news) {
                NewsFragment newsFragment = new NewsFragment();
                fragmentTransaction.replace(R.id.fragment_layout, newsFragment, "news");
            } else if (menuItemId == R.id.navigation_about) {
                AboutFragment aboutFragment = new AboutFragment();
                fragmentTransaction.replace(R.id.fragment_layout, aboutFragment, "about");
            }

            fragmentTransaction.commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            HomeFragment homeFragment = new HomeFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_layout, homeFragment, "home");
            fragmentTransaction.commit();
        }


        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
