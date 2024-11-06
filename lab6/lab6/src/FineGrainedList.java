public class FineGrainedList<T> {
    Node<T> head;

    public FineGrainedList() {
        head = new Node<>(null);
    }

    public boolean contains(Node<T> node) {
        var current = head.next;

        while (current != null) {
            current.lock.lock();
            try {
                if (current == node) return true;
                var next = current.next;
                if (next != null) next.lock.lock();
                current = next;
            } finally {
                if (current != null) current.lock.unlock();
            }
        }

        return false;
    }

    public boolean remove(Node<T> node) {
        Node<T> current = head;
        current.lock.lock();
        try {
            while (current.next != null) {
                Node<T> next = current.next;
                next.lock.lock();
                try {
                    if (next == node) {
                        current.next = next.next;
                        return true;
                    }
                    current = next;
                } finally {
                    next.lock.unlock();
                }
            }
        } finally {
            current.lock.unlock();
        }
        return false;
    }

    public boolean add(Node<T> node) {
        var current = head;

        head.lock.lock();

        try {
            while (current.next != null) {
                var next = current.next;
                next.lock.lock();
                current.lock.unlock();
                current = next;
            }
            current.next = node;

            return true;
        } finally {
            current.lock.unlock();
        }
    }
}
