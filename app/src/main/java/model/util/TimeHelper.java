package model.util;

import java.time.LocalTime;
import java.time.temporal.ChronoField;

public class TimeHelper {

    public LocalTime fromMillis(long millis) {
        LocalTime time = LocalTime.ofSecondOfDay(millisToSeconds(millis));
        return time.with(ChronoField.MILLI_OF_SECOND, (int) (millisLeftAfterSeconds(millis)));
    }

    public long nowMillis() {
        return nanosToMillis(System.nanoTime());
    }

    private long nanosToMillis(long nanos) {
        return nanos / 1_000_000;
    }

    private long millisToSeconds(long millis) {
        return millis / 1000;
    }

    private long millisLeftAfterSeconds(long millis) {
        return millis % 1000;
    }

}
