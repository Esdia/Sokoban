package esdia.sokoban.sequences;

public interface Iterators {
    boolean hasNext();
    int next();

    // Deletes the last element returned by next()
    void delete();
}
