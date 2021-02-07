package esdia.sokoban.sequences;

class Link<Type> {
    Type value;
    Link<Type> next;

    Link(Type value, Link<Type> next) {
        this.value = value;
        this.next = next;
    }
}
