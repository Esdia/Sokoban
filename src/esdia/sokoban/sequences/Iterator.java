package esdia.sokoban.sequences;

public interface Iterator<Type> {
    boolean hasNext();
    Type next();

    // Deletes the last element returned by next()
    void delete();
}
