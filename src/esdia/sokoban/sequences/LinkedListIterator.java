package esdia.sokoban.sequences;

import java.util.NoSuchElementException;

public class LinkedListIterator implements Iterator {
    LinkedList l;
    Link current;

    /*
    * We have pprevious -> previous -> current
    * Since delete() deletes the last value returned by
    * next(), it will delete previous, hence the need to
    * remember pprevious
    * */
    Link previous, pprevious;

    public LinkedListIterator(LinkedList l) {
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
    public int next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }

        int val = this.current.value;

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
