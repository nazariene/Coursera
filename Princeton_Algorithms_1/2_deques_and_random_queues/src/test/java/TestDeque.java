import org.junit.Assert;
import org.junit.Test;

public class TestDeque {

    @Test
    public void testConstructor() {
        Deque<String> deque = new Deque<>();
        Assert.assertTrue("Should be empty", deque.isEmpty());
        Assert.assertEquals("Size should be zero", 0, deque.size());
    }

    @Test
    public void testAddFirst() {
        Deque<String> deque = new Deque<>();
        deque.addFirst("111");
        Assert.assertTrue("Should not be empty", !deque.isEmpty());
        Assert.assertEquals("Size should be 1", 1, deque.size());
    }

    @Test
    public void testAddLast() {
        Deque<String> deque = new Deque<>();
        deque.addLast("111");
        Assert.assertTrue("Should not be empty", !deque.isEmpty());
        Assert.assertEquals("Size should be 1", 1, deque.size());
    }

    @Test
    public void testRemoveFirst() {
        Deque<String> deque = new Deque<>();
        deque.addFirst("111");
        deque.addFirst("222");
        String result = deque.removeFirst();
        Assert.assertEquals("Size should be 1", 1, deque.size());
        Assert.assertEquals("222", result);
    }

    @Test
    public void testRemoveLast() {
        Deque<String> deque = new Deque<>();
        deque.addFirst("111");
        deque.addFirst("222");
        String result = deque.removeLast();
        Assert.assertEquals("Size should be 1", 1, deque.size());
        Assert.assertEquals("111", result);
    }

    @Test
    public void testRemoveLastWithOneElement() {
        Deque<String> deque = new Deque<>();
        deque.addFirst("111");
        String result = deque.removeLast();
        Assert.assertTrue("Should be empty", deque.isEmpty());
        Assert.assertEquals("Size should be 0", 0, deque.size());
        Assert.assertEquals("111", result);
    }

}
