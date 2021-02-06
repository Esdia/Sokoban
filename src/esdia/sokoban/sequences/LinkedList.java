package esdia.sokoban.sequences;

public class LinkedList implements Sequence {
    Link head;
    Link tail;

    public LinkedList() {
        this.head = null;
        this.tail = null;
    }

    @Override
    public void insertHead(int e) {
        Link c = new Link(e, this.head);

        if (this.isEmpty()) {
            this.tail = c;
        }
        this.head = c;
    }

    @Override
    public void insertTail(int e) {
        Link c = new Link(e, null);
        if (this.isEmpty()) {
            this.head = c;
        } else {
            this.tail.next = c;
        }

        this.tail = c;
    }

    @Override
    public int getHead() {
        if (this.isEmpty()) {
            throw new RuntimeException("Empty Sequence");
        }

        int res = this.head.value;
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
        Link c = this.head;
        int val;
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

    public LinkedListIterator iterator() {
        return new LinkedListIterator(this);
    }
}
