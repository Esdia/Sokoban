package esdia.sokoban.sequences;

public class PriorityQueueTable<Type extends Comparable<Type>> extends PriorityQueue<Type> {
    TableSequence<Type> s;

    public PriorityQueueTable() {
        this.s = new TableSequence<>();
        super.s = this.s;
    }

    private int get_index(int i) {
        return i % this.s.table.length;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void insert(Type e) {
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

        while (next != this.s.head && ((Type) this.s.table[current]).compareTo(e) > 0) {
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
