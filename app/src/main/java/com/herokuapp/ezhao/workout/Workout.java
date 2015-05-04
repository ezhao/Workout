package com.herokuapp.ezhao.workout;

import android.app.Activity;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Table(name = "Workout")
public class Workout extends Model implements Serializable {
    @Column(name = "workoutName") private String workoutName;
    @Column(name = "imageA") private String imageA;
    @Column(name = "imageB") private String imageB;

    public Workout() {
        super();
    }

    public Workout(String workoutName, String imageA, String imageB) {
        this.workoutName = workoutName;
        this.imageA = imageA;
        this.imageB = imageB;
    }

    public static List<Workout> fromTxtFile(Activity activity) {
        List<Workout> workouts = new ArrayList<>();
        try {
            // Get raw file from assets
            InputStream inputStream = activity.getAssets().open("workouts.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            // Turn into workout objects
            String line = bufferedReader.readLine();
            while (line != null) {
                int firstBreak = line.indexOf(",");
                int secondBreak = line.indexOf(",", firstBreak + 1);

                String workoutName = line.substring(0, firstBreak);
                String imageA = line.substring(firstBreak + 1, secondBreak);
                String imageB = line.substring(secondBreak + 1);
                Workout workout = new Workout(workoutName, imageA, imageB);
                workout.save();

                workouts.add(workout);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workouts;
    }

    public static List<Workout> getAll(Activity activity) {
        List<Workout> workouts = new Select().from(Workout.class).execute();
        if (workouts.size() == 0) {
            workouts = Workout.fromTxtFile(activity);
        }
        return workouts;
    }

    public String getWorkoutName() {
        return workoutName;
    }
}
