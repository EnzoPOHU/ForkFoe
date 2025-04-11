package com.forkfoe.forkfoe.util;

public class ServiceTimer {
    static { new ServiceTimer(); };

    private static long startTime;
    private static long elapsedTime;
    private static boolean running;
    private static long countdownTime;

    private ServiceTimer() {
        reset();
    }

    public static void start() {
        if (!running) {
            if (countdownTime > 0) {
                startTime = System.currentTimeMillis();
            } else {
                startTime = System.currentTimeMillis() - elapsedTime;
            }
            running = true;
        }
    }

    public static void pause() {
        if (running) {
            if (countdownTime > 0) {
                countdownTime -= (System.currentTimeMillis() - startTime);
                if (countdownTime <= 0) {
                    countdownTime = 0;
                }
            } else {
                elapsedTime = System.currentTimeMillis() - startTime;
            }
            running = false;
        }
    }

    public static void reset() {
        startTime = 0;
        elapsedTime = 0;
        running = false;
        countdownTime = 15 * 60 * 1000; // 25 minutes in milliseconds
    }

    public static String getElapsedTime() {
        long currentElapsedTime;
        if (countdownTime > 0) {
            if (running) {
                long timeLeft = countdownTime - (System.currentTimeMillis() - startTime);
                if (timeLeft <= 0) {
                    running = false;
                    return "00:00";
                }
                currentElapsedTime = timeLeft;
            } else {
                currentElapsedTime = countdownTime;
            }
        } else {
            currentElapsedTime = running ? System.currentTimeMillis() - startTime : elapsedTime;
        }

        long elapsedSeconds = currentElapsedTime / 1000;
        long minutes = elapsedSeconds / 60;
        long seconds = elapsedSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
