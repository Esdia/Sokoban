package esdia.sokoban.sequences;

public abstract class PriorityQueue<V, P extends Comparable<P>> {
    Sequence<Couple<V, P>> s;

    public PriorityQueue() {
        this.s = new LinkedList<>();
    }

    public abstract void insert(Couple<V, P> e);

    public void insert(V value, P priority) {
        Couple<V, P> c = new Couple<>(value, priority);
        this.insert(c);
    }

    public V extract() {
        return s.getHead().value;
    }

    public boolean isEmpty() {
        return s.isEmpty();
    }
}
