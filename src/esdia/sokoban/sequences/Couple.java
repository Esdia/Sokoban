package esdia.sokoban.sequences;

class Couple<V, P extends Comparable<P>> {
    V value;
    P priority;

    Couple(V value, P priority) {
        this.value = value;
        this.priority = priority;
    }

    public int compareTo(Couple<V, P> c) {
        return this.priority.compareTo(c.priority);
    }
}
