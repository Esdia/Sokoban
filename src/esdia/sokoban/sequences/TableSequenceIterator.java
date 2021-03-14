package esdia.sokoban.sequences;

import java.util.NoSuchElementException;

public class TableSequenceIterator<Type> implements Iterator<Type> {
    private final TableSequence<Type> s;

    private int rank, position, last;

    private boolean can_delete;

    TableSequenceIterator(TableSequence<Type> s) {
        this.s = s;
        this.rank = 0;
        this.position = this.s.head;
        this.last = -1;
        this.can_delete = false;
    }

    @Override
    public boolean hasNext() {
        return this.rank < this.s.size;
    }

    @Override
    public Type next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        this.can_delete = true;

        this.last = this.position;
        this.position = (this.position + 1) % this.s.table.length;
        this.rank++;

        @SuppressWarnings("unchecked")
        Type val = (Type) this.s.table[this.last];
        return val;
    }

    @Override
    public void delete() {
        if (!this.can_delete) {
            throw new IllegalStateException();
        }

        this.can_delete = false;

        this.position = last;

        int current = this.rank;
        while (current < this.s.size) {
            int next = (this.last + 1) % this.s.table.length;
            this.s.table[last] = this.s.table[next];
            this.last = next;
            current++;
        }
        this.last = -1;
        this.rank--;
        this.s.size--;
    }
}
