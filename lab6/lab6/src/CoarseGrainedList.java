import java.util.concurrent.locks.ReentrantLock;

public class CoarseGrainedList<T> {
    Node<T> head;
    ReentrantLock lock;

    public CoarseGrainedList() {
        head = new Node<>(null);
        lock = new ReentrantLock();
    }

    public boolean contains(Node<T> node) {
        lock.lock();

        try {
            var currentNode = head.next;

            while (currentNode != null) {
                if (currentNode == node) {
                    return true;
                }

                currentNode = currentNode.next;
            }

            return false;
        } finally {
            lock.unlock();
        }
    }

    public boolean remove(Node<T> node) {
        lock.lock();

        try {
            var node1 = head;
            var node2 = head.next;

            while (node2 != null) {
                if (node2 == node) {
                    node1.next = node2.next;
                    return true;
                }

                node1 = node2;
                node2 = node2.next;
            }

            return false;
        } finally {
            lock.unlock();
        }
    }

    public boolean add(Node<T> node) {
        lock.lock();

        try {
            var currentNode = head.next;

            while (currentNode.next != null) {
                currentNode = currentNode.next;
            }

            currentNode.next = node;

            return true;
        } finally {
            lock.unlock();
        }
    }
}
