package junit.time.test;

import model.util.TimeHelper;

public class TestTimeHelper  extends TimeHelper {

    long currentTimeMillis = 0;

    @Override
    public long nowMillis() {
        return currentTimeMillis;
    }

    public void setNowMillis(long currentTimeMillis) {
        this.currentTimeMillis = currentTimeMillis;
    }
}
