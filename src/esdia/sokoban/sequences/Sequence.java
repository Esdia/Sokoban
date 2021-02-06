package esdia.sokoban.sequences;

public interface Sequence {
    void insertHead(int e);
    void insertTail(int e);

    int getHead();

    boolean isEmpty();

    Iterator iterator();
}
