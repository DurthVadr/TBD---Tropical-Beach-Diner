package GameEngine;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class TimeManager {
    private int totalTime; // Total time in seconds
    private Timer timer;
    private JLabel timerLabel;
    private TimerTask timerTask;


    public TimeManager(JLabel timerLabel) {
        this.timerLabel = timerLabel;
    }

    public void startTimer(int seconds) {
        seconds=seconds+1;
        totalTime = seconds; // Initialize total time with the given seconds
        timer = new Timer();
        createNewTimerTask();
        timer.scheduleAtFixedRate(timerTask, 0, 1000); // Schedule task to run every second
    }

    private void createNewTimerTask() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (totalTime > 0) {
                    totalTime--;
                    updateTimerLabel();
                } else {
                    timer.cancel();
                    // Handle timer end (e.g., end the game)
                }
            }
        };
    }

    private void updateTimerLabel() {
        int minutes = totalTime / 60;
        int seconds = totalTime % 60;
        SwingUtilities.invokeLater(() -> timerLabel.setText(String.format("Time: %02d:%02d", minutes, seconds)));
    }

    public void pauseTimer() {
        if (timer != null) {
            timerTask.cancel();
        }
    }

    public void resumeTimer() {
        if (timer != null) {
            createNewTimerTask();
            timer.scheduleAtFixedRate(timerTask, 0, 1000);
        }
    }
}
