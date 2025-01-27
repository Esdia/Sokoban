package esdia.sokoban.sequences;

public class TestIterator {
    static void test_iterator(Sequence<Integer> s) {
        for (int i = 50; i < 100; i++) {
            s.insertTail(i);
            assert !s.isEmpty();
        }

        for (int i = 49; i >= 0; i--) {
            s.insertHead(i);
            assert !s.isEmpty();
        }

        Iterator<Integer> it = s.iterator();

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
        Sequence<Integer> s = new LinkedList<>();
        test_iterator(s);
        s = new TableSequence<>();
        test_iterator(s);
    }
}
