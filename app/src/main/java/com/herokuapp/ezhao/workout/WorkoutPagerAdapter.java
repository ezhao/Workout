package com.herokuapp.ezhao.workout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.List;

public class WorkoutPagerAdapter extends FragmentStatePagerAdapter {
    private List<Workout> workouts;

    public WorkoutPagerAdapter(FragmentManager fm, List<Workout> workouts) {
        super(fm);
        this.workouts = workouts;
    }

    @Override
    public Fragment getItem(int position) {
        Workout workout = workouts.get(position);
        return WorkoutFragment.newInstance(workout);
    }

    @Override
    public int getCount() {
        return workouts.size();
    }
}
