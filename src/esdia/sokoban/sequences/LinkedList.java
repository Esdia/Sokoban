package esdia.sokoban.sequences;

public class LinkedList<Type> implements Sequence<Type> {
    Link<Type> head;
    Link<Type> tail;

    public LinkedList() {
        this.head = null;
        this.tail = null;
    }

    @Override
    public void insertHead(Type e) {
        Link<Type> c = new Link<>(e, this.head);

        if (this.isEmpty()) {
            this.tail = c;
        }
        this.head = c;
    }

    @Override
    public void insertTail(Type e) {
        Link<Type> c = new Link<>(e, null);
        if (this.isEmpty()) {
            this.head = c;
        } else {
            this.tail.next = c;
        }

        this.tail = c;
    }

    @Override
    public Type getHead() {
        if (this.isEmpty()) {
            throw new RuntimeException("Empty Sequence");
        }

        Type res = this.head.value;
        this.head = this.head.next;

        if (this.isEmpty()) {
            this.tail = null;
        }

        return res;
    }

    @Override
    public boolean isEmpty() {
        return this.head == null;
    }

    public String toString() {
        StringBuilder out = new StringBuilder("[");
        Link<Type> c = this.head;
        Type val;
        while (c != null) {
            val = c.value;
            out.append(val);

            c = c.next;
            if (c != null) {
                out.append(", ");
            }
        }
        out.append("]");

        return out.toString();
    }

    public LinkedListIterator<Type> iterator() {
        return new LinkedListIterator<>(this);
    }
}
