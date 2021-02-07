package esdia.sokoban.sequences;

public class PriorityQueueTable<V, P extends Comparable<P>> extends PriorityQueue<V, P> {
    TableSequence<Couple<V, P>> s;

    public PriorityQueueTable() {
        this.s = new TableSequence<>();
        super.s = this.s;
    }

    @Override
    @SuppressWarnings("unchecked")
    void insert(Couple<V, P> e) {
        if (this.isEmpty()) {
            this.s.insertHead(e);
            return;
        }

        if (this.s.size == this.s.table.length) {
            this.s.resize();
        }

        int next = (this.s.head + this.s.size) % this.s.table.length;
        int current = next - 1;

        if (current < 0) {
            current = this.s.table.length - 1;
        }

        while (next != this.s.head && ((Couple<V, P>) this.s.table[current]).compareTo(e) > 0) {
            this.s.table[next] = this.s.table[current];

            next = current;
            current--;

            if (current < 0) {
                current = this.s.table.length - 1;
            }
        }

        this.s.table[next] = e;
        this.s.size++;
    }
}
