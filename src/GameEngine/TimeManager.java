package GameEngine;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class TimeManager {
    private int totalTime; // Total time in seconds
    private Timer timer;

    private boolean isPaused = false;


    public void setTimerLabel(JLabel timerLabel) {
        this.timerLabel = timerLabel;
    }

    private JLabel timerLabel;
    private TimerTask timerTask;

    public TimeManager(JLabel timerLabel) {
        this.timerLabel = timerLabel;
    }

    public TimeManager() {
    }


    public void startTimer(int seconds) {
        totalTime = seconds+1; // Initialize total time with the given seconds
        //+1 second is added so that player can feel the actual time.
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
        if (timerTask != null) {
            timerTask.cancel();
        }
    }

    public void resumeTimer() {
        if (timer != null) {
            timer.cancel(); // Cancel the existing timer to ensure a fresh start
        }
        timer = new Timer();
        createNewTimerTask();
        timer.scheduleAtFixedRate(timerTask, 1000, 1000); // Start after 1 second, then run every second
    }

    public void resetTimer() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }
}
