package esdia.sokoban.sequences;

public interface Iterator {
    boolean hasNext();
    int next();

    // Deletes the last element returned by next()
    void delete();
}
