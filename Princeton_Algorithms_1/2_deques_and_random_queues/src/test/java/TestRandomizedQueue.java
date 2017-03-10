import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;

public class TestRandomizedQueue {

    @Test
    public void checkRandomCalls1() {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        Assert.assertTrue(rq.isEmpty());
        rq.enqueue(1);
        rq.enqueue(1);
        rq.dequeue();
        Assert.assertFalse(rq.isEmpty());
    }

    @Test
    public void testSizeAfterEnqueueOnly() {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.enqueue(0);
        rq.enqueue(0);
        rq.enqueue(4);
        rq.enqueue(1);
        Assert.assertEquals(4, rq.size());
    }

    @Test
    public void testDequeueSequence() {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.enqueue(151);
        Assert.assertEquals(1, rq.size());
        rq.enqueue(153);
        Assert.assertEquals(2, rq.size());
        rq.enqueue(113);
        Assert.assertFalse(rq.isEmpty());
        rq.dequeue();
        Assert.assertEquals(2, rq.size());
        rq.enqueue(621);
        rq.dequeue();
        rq.enqueue(292);
        Assert.assertEquals(3, rq.size());
    }

    @Test
    public void testEnqueDequeSequence() {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.enqueue(31);
        Assert.assertEquals(1, rq.size());
        rq.enqueue(28);
        rq.enqueue(30);
        rq.dequeue();
        rq.enqueue(10);
        rq.enqueue(32);
        Assert.assertEquals(4, rq.size());
    }

    @Test
    public void testEmptyIterator() {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        Iterator<Integer> iterator = rq.iterator();
    }
}
