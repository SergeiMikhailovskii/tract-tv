package com.mikhailovskii.trakttv.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.mikhailovskii.trakttv.R;
import com.mikhailovskii.trakttv.ui.movies_list.MoviesListFragment;
import com.mikhailovskii.trakttv.ui.profile.ProfileFragment;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {

            Fragment fragment = null;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            switch (menuItem.getItemId()) {

                case R.id.movies:
                    menuItem.setChecked(true);
                    fragment = new MoviesListFragment();
                    break;

                case R.id.favorites:
                    menuItem.setChecked(true);
                    break;

                case R.id.profile:
                    menuItem.setChecked(true);
                    fragment = new ProfileFragment();
                    break;
            }

            if (fragment != null) {
                fragmentTransaction.replace(R.id.fragments, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commit();
            }

            return false;
        });
    }

}
