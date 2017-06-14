import java.util.concurrent.CountDownLatch;

public class ValueLatch<T> {

    private T value = null;

    private final CountDownLatch latch = new CountDownLatch(1);

    public boolean isSet() {
        return (latch.getCount() == 0);
    }

    public synchronized void setValue(T value) {
        if (!isSet()) {
            this.value = value;
            latch.countDown();
        }
    }

    public T getValue() throws InterruptedException {
        latch.await();
        synchronized (this) {
            return value;
        }
    }
}
