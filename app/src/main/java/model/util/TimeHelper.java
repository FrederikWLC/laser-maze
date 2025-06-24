package model.util;


public class TimeHelper {

    public long nowMillis() {
        return nanosToMillis(System.nanoTime());
    }

    private long nanosToMillis(long nanos) {
        return nanos / 1_000_000;
    }


}
