package com.pizzashop.client.util;

import java.util.Timer;
import java.util.TimerTask;

public class CountDownTimer {
    static int interval;
    static Timer timer;

    public static void startCountDown(String secs) {
        int delay = 1000;
        int period = 1000;
        timer = new Timer();
        interval = Integer.parseInt(secs);
        System.out.println(secs);
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                System.out.println(setInterval());

            }
        }, delay, period);
    }

    private static final int setInterval() {
        if (interval == 1)
            timer.cancel();
        return --interval;
    }
}
