package com.herokuapp.ezhao.workout;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.ArrayList;
import java.util.List;

public class WorkoutPagerAdapter extends FragmentStatePagerAdapter {
    private List<Workout> workouts;
    private ArrayList<WorkoutFragment> fragments;

    public WorkoutPagerAdapter(FragmentManager fm, List<Workout> workouts) {
        super(fm);
        this.workouts = workouts;

        fragments = new ArrayList<>(workouts.size());
        for (int i = 0; i < workouts.size(); i++) {
            fragments.add(null);
        }
    }

    @Override
    public WorkoutFragment getItem(int position) {
        if (fragments.get(position) == null) {
            Workout workout = workouts.get(position);
            WorkoutFragment workoutFragment = WorkoutFragment.newInstance(workout);
            fragments.add(position, workoutFragment);
        }
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return workouts.size();
    }
}
