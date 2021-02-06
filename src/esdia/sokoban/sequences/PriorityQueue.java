package esdia.sokoban.sequences;

public abstract class PriorityQueue<Type extends Comparable<Type>> {
    Sequence<Type> s;

    public PriorityQueue() {
        this.s = new LinkedList<>();
    }

    public abstract void insert(Type e);

    public Type extract() {
        return s.getHead();
    }

    public boolean isEmpty() {
        return s.isEmpty();
    }
}
