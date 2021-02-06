package esdia.sokoban.sequences;

public interface Sequence<Type> {
    void insertHead(Type e);
    void insertTail(Type e);

    Type getHead();

    boolean isEmpty();

    Iterator<Type> iterator();
}
