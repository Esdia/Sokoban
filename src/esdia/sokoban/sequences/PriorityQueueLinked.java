package esdia.sokoban.sequences;

public class PriorityQueueLinked<Type extends Comparable<Type>> extends PriorityQueue<Type> {
    LinkedList<Type> s;

    public PriorityQueueLinked() {
        this.s = new LinkedList<>();
        super.s = this.s;
    }

    @Override
    public void insert(Type e) {
        if (this.isEmpty()) {
            this.s.insertHead(e);
            return;
        }

        Link<Type> link = this.s.head;

        if (link.value.compareTo(e) > 0) {
            this.s.insertHead(e);
            return;
        }

        while (link.next != null && link.next.value.compareTo(e) < 0) {
            link = link.next;
        }

        link.next = new Link<>(e, link.next);
    }
}
