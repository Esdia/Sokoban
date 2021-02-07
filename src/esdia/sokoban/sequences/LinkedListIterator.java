package esdia.sokoban.sequences;

import java.util.NoSuchElementException;

public class LinkedListIterator<Type> implements Iterator<Type> {
    private final LinkedList<Type> l;
    private Link<Type> current;

    /*
    * We have pprevious -> previous -> current
    * Since delete() deletes the last value returned by
    * next(), it will delete previous, hence the need to
    * remember pprevious
    * */
    private Link<Type> previous, pprevious;

    LinkedListIterator(LinkedList<Type> l) {
        this.l = l;
        this.current = l.head;
        this.previous = null;
        this.pprevious = null;
    }

    @Override
    public boolean hasNext() {
        return this.current != null;
    }

    @Override
    public Type next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }

        Type val = this.current.value;

        if (this.previous != null) {
            this.pprevious = this.previous;
        }
        this.previous = this.current;
        this.current = this.current.next;

        return val;
    }

    @Override
    public void delete() {
        if (this.previous == null) {
            throw new IllegalStateException();
        }

        if (this.pprevious == null) {
            // next() has been called only once
            this.l.head = this.current;
        } else {
            this.pprevious.next = this.current;
        }

        this.previous = null;
    }
}
