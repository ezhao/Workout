package com.herokuapp.ezhao.workout;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class WorkoutFragment extends Fragment {
    private Workout workout;
    @InjectView(R.id.rlBackground) RelativeLayout rlBackground;
    @InjectView(R.id.tvWorkoutName) TextView tvWorkoutName;
    @InjectView(R.id.tvStatus) TextView tvStatus;
    @InjectView(R.id.tvTimer) TextView tvTimer;
    private long startTime;
    private Timer timer;
    private Activity activity;
    private final int PREP_TIME = 5;
    private final int GO_TIME = 30;

    public static WorkoutFragment newInstance(Workout workout) {
        WorkoutFragment workoutFragment = new WorkoutFragment();
        Bundle args = new Bundle();
        args.putSerializable("workout", workout);
        workoutFragment.setArguments(args);
        return workoutFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
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
        }

        return view;
    }

    public void timePrep() {
        // Prep time
        startTime = System.currentTimeMillis();

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvStatus.setText(getResources().getString(R.string.prep_string));
                rlBackground.setBackgroundColor(getResources().getColor(R.color.prep_color));
            }
        });

        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int remains = (int) (startTime + PREP_TIME * 1000 - System.currentTimeMillis());
                setTvTimer(remains);
                if (remains < 0) {
                    cancel();
                    timeGo();
                }
            }
        }, 0, 9);
    }

    public void timeGo() {
        // Go time
        startTime = System.currentTimeMillis();

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvStatus.setText(getResources().getString(R.string.go_string));
                rlBackground.setBackgroundColor(getResources().getColor(R.color.go_color));
            }
        });

        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int remains = (int) (startTime + GO_TIME * 1000 - System.currentTimeMillis());
                setTvTimer(remains);
                if (remains < 0) {
                    cancel();
                    // TODO(emily) Next card!
                    activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                }
            }
        }, 0, 9);

        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void setTvTimer(int timeRemaining) {
        int hundredths = (timeRemaining / 10) % 100;
        int seconds = (timeRemaining / 1000) % 60;
        int minutes = (timeRemaining / (1000 * 60)) % 60;

        final String newTime = String.format("%02d:%02d:%02d", minutes, seconds, hundredths);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvTimer.setText(newTime);
            }
        });
    }
}
