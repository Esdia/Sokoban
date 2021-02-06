package esdia.sokoban.sequences;

public class TableSequence implements Sequence {
    int[] table;
    int size;
    int head;

    public TableSequence() {
        this.table = new int[1];
        this.size = 0;
        this.head = 0;
    }

    public void resize() {
        int[] new_table = new int[2 * this.size];

        int size_bkp = this.size;
        int i = 0;
        while (!this.isEmpty()) {
            new_table[i] = this.getHead();
            i++;
        }

        this.head = 0;
        this.size = size_bkp;
        this.table = new_table;
    }

    @Override
    public void insertHead(int e) {
        if (this.size == this.table.length) {
            this.resize();
        }

        this.head--;
        if (this.head < 0) {
            this.head = this.table.length - 1;
        }

        this.table[head] = e;
        this.size++;
    }

    @Override
    public void insertTail(int e) {
        if (this.size == this.table.length) {
            this.resize();
        }

        int index = (this.head + this.size) % this.table.length;
        this.table[index] = e;
        this.size++;
    }

    @Override
    public int getHead() {
        if (this.isEmpty()) {
            throw new RuntimeException("Empty Sequence");
        }

        int val = this.table[this.head];
        this.head++;
        if (this.head >= this.table.length) {
            this.head = 0;
        }
        this.size--;

        return val;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }
}
