package com.herokuapp.ezhao.workout;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends FragmentActivity {
    @InjectView(R.id.vpFragments) ViewPager vpFragments;
    WorkoutPagerAdapter workoutPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        List<Workout> workouts = Workout.getAll(this);

        FragmentManager fm = getSupportFragmentManager();
        workoutPagerAdapter = new WorkoutPagerAdapter(fm, workouts);
        vpFragments.setAdapter(workoutPagerAdapter);

        vpFragments.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("EMILY", "onPageSelected" + position);
                WorkoutFragment workoutFragment = workoutPagerAdapter.getItem(position);
                workoutFragment.timePrep();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
