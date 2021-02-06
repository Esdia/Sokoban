package esdia.sokoban.sequences;

import java.util.NoSuchElementException;

public class TableSequenceIterator implements Iterator {
    TableSequence s;
    int current;

    boolean can_delete;

    public TableSequenceIterator(TableSequence s) {
        this.s = s;
        this.current = 0;
        this.can_delete = false;
    }

    @Override
    public boolean hasNext() {
        return this.current < this.s.size;
    }

    @Override
    public int next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        this.can_delete = true;

        int val = this.s.table[this.current];
        this.current++;
        return val;
    }

    @Override
    public void delete() {
        if (!this.can_delete) {
            throw new IllegalStateException();
        }

        this.can_delete = false;

        // Current cannot be equal to zero if you can delete
        this.current--;
        System.arraycopy(this.s.table, this.current + 1, this.s.table, this.current, this.s.size - 1 - this.current);
        this.s.size--;

    }
}
