package esdia.sokoban.sequences;

import java.util.NoSuchElementException;

public class TableSequenceIterator<Type> implements Iterator<Type> {
    TableSequence<Type> s;
    int current;

    boolean can_delete;

    public TableSequenceIterator(TableSequence<Type> s) {
        this.s = s;
        this.current = 0;
        this.can_delete = false;
    }

    @Override
    public boolean hasNext() {
        return this.current < this.s.size;
    }

    @Override
    public Type next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        this.can_delete = true;

        @SuppressWarnings("unchecked")
        Type val = (Type) this.s.table[this.current];
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
