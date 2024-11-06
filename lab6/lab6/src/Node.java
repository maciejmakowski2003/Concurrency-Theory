import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

public class Node<T> {
    public final T value;
    public Node<T> next = null;
    public final ReentrantLock lock;

    public Node(T value) {
        this.value = value;
        lock = new ReentrantLock();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?> node = (Node<?>) o;
        return Objects.equals(value, node.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, next, lock);
    }
}
