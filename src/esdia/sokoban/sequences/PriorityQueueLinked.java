package esdia.sokoban.sequences;

public class PriorityQueueLinked<V, P extends Comparable<P>> extends PriorityQueue<V, P> {
    LinkedList<Couple<V, P>> s;

    public PriorityQueueLinked() {
        this.s = new LinkedList<>();
        super.s = this.s;
    }

    @Override
    public void insert(Couple<V, P> e) {
        if (this.isEmpty()) {
            this.s.insertHead(e);
            return;
        }

        Link<Couple<V, P>> link = this.s.head;

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
