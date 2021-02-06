package esdia.sokoban.sequences;

import java.util.NoSuchElementException;

public class LinkedListIterator implements Iterators {
    LinkedList l;
    Link current;

    /*
    * We have pprevious -> previous -> current
    * Since delete() deletes the last value returned by
    * next(), it will delete previous, hence the need to
    * remember pprevious
    * */
    Link previous, pprevious;

    boolean can_delete;

    public LinkedListIterator(LinkedList l) {
        this.l = l;
        this.current = l.head;
        this.previous = null;
        this.pprevious = null;
        this.can_delete = false;
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

        this.pprevious = this.previous;
        this.previous = this.current;
        this.current = this.current.next;
        this.can_delete = true;

        return val;
    }

    @Override
    public void delete() {
        if (!this.can_delete) {
            throw new IllegalStateException();
        }

        this.can_delete = false;

        if (this.pprevious == null) {
            // next() has been called only once
            this.l.head = this.previous;
            return;
        }

        this.previous.next = this.current;
    }
}
