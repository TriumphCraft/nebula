package me.mattstudios.mattscore.utils;

import java.util.concurrent.TimeUnit;

public class TimeUtils {

    /**
     * Gets the difference in seconds between times
     *
     * @param storedTime the stored time to compare
     * @return returns the difference in seconds
     */
    public static long getSecondsDifference(long storedTime) {
        if (storedTime == 0) return 0;
        return TimeUnit.SECONDS.convert((System.currentTimeMillis() - storedTime), TimeUnit.MILLISECONDS);
    }

}
