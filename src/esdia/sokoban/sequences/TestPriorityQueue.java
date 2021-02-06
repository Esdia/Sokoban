package esdia.sokoban.sequences;

import java.util.Random;

public class TestPriorityQueue {
    static void test_priority(PriorityQueue<Integer> p) {
        Random r = new Random();
        int max = 100, min = 0;

        assert p.isEmpty();

        for (int i = 0; i < 100; i++) {
            p.insert(r.nextInt(max - min + 1) + min);
        }

        int n = p.extract();
        int tmp;
        for (int i = 0; i < 99; i++) {
            tmp = p.extract();
            assert n <= tmp;
            n = tmp;
        }

        assert p.isEmpty();

        System.out.println("OK");
    }

    public static void main(String[] args) {
        PriorityQueueLinked<Integer> p = new PriorityQueueLinked<>();
        test_priority(p);
        // TODO add test for PriorityQueueTable
    }
}
