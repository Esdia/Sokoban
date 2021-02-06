package esdia.sokoban.sequences;

public class TestIterator {
    static void test_iterator(Sequence s) {
        for (int i = 0; i < 100; i++) {
            s.insertTail(i);
        }

        Iterator it = s.iterator();

        assert it.hasNext();

        for (int i = 0; i < 100; i++) {
            assert it.hasNext();
            assert it.next() == i;
            it.delete();
        }
        assert !it.hasNext();
        assert s.isEmpty();

        it = s.iterator();
        assert !it.hasNext();

        boolean ok = true;

        s.insertHead(1);
        s.insertTail(2);
        it = s.iterator();
        try {
            it.delete();
            System.out.println("NOT OK : Shouldn't be able to delete before call to next");
            ok = false;
        } catch (IllegalStateException ignored) {}

        it.next();
        it.delete();
        try {
            it.delete();
            System.out.println("NOT OK : Shouldn't be able to delete twice after one call to next");
            ok = false;
        } catch (IllegalStateException ignored) {}

        if (ok) {
            System.out.println("OK");
        }
    }

    public static void main(String[] args) {
        Sequence s = new LinkedList();
        test_iterator(s);
        // TODO add test for the TableSequenceIterator
    }
}
