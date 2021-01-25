package esdia.sokoban.sequences;

public class TestSequence {
    public static void main(String[] args) {
        Sequence s = new LinkedList();

        assert s.isEmpty();

        for (int i = 49; i >= 0; i--) {
            s.insertHead(i);
            assert !s.isEmpty();
        }

        for (int i = 50; i < 100; i++) {
            s.insertTail(i);
            assert !s.isEmpty();
        }

        int val;
        for (int i = 0; i < 100; i++) {
            val = s.getHead();
            assert val == i;
        }

        assert s.isEmpty();

        try {
            val = s.getHead();
            System.out.println("NOT OK. Should be empty, contains " + val);
        } catch (RuntimeException e) {
            System.out.println("OK");
        }
    }
}