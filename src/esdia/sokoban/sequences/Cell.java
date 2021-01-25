package esdia.sokoban.sequences;

public class Cell {
    int value;
    Cell next;

    public Cell(int value, Cell next) {
        this.value = value;
        this.next = next;
    }
}
