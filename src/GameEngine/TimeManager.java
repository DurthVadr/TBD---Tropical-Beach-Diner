package GameEngine;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class TimeManager {
    private int totalTime; // Total time in seconds
    private Timer timer;
    private JLabel timerLabel;

    public TimeManager(JLabel timerLabel) {
        this.timerLabel = timerLabel;
    }

    public void startTimer(int seconds) {
        seconds=seconds+1;
        totalTime = seconds; // Convert minutes to seconds
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
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
        }, 0, 1000); // Schedule task to run every second
    }

    private void updateTimerLabel() {
        int minutes = totalTime / 60;
        int seconds = totalTime % 60;
        SwingUtilities.invokeLater(() -> timerLabel.setText(String.format("Time: %02d:%02d", minutes, seconds)));
    }
}
