package com.herokuapp.ezhao.workout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class WorkoutFragment extends Fragment {
    private Workout workout;
    @InjectView(R.id.tvWorkoutName) TextView tvWorkoutName;
    @InjectView(R.id.tvStatus) TextView tvStatus;

    public static WorkoutFragment newInstance(Workout workout) {
        WorkoutFragment workoutFragment = new WorkoutFragment();
        Bundle args = new Bundle();
        args.putSerializable("workout", workout);
        workoutFragment.setArguments(args);
        return workoutFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        workout = (Workout) getArguments().getSerializable("workout");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout, container, false);
        ButterKnife.inject(this, view);

        if (workout != null) {
            tvWorkoutName.setText(workout.getWorkoutName());
            tvStatus.setText("GO!");
        }

        return view;
    }
}
