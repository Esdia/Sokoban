package esdia.sokoban.sequences;

public class Link<Type> {
    Type value;
    Link<Type> next;

    public Link(Type value, Link<Type> next) {
        this.value = value;
        this.next = next;
    }
}
