package esdia.sokoban.sequences;

public class Couple<V, P extends Comparable<P>> {
    V value;
    P priority;

    public Couple(V value, P priority) {
        this.value = value;
        this.priority = priority;
    }

    public int compareTo(Couple<V, P> c) {
        return this.priority.compareTo(c.priority);
    }
}
