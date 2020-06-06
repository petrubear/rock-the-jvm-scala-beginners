package exercises;

import java.util.function.Function;
import java.util.function.Predicate;

public abstract class MyJList<Integer> {
    protected Integer head;
    protected MyJList<Integer> tail;

    protected abstract boolean isEmpty();

    protected abstract MyJList<Integer> add(Integer element);

    protected abstract String printElements();

    @Override
    public String toString() {
        return "[" + printElements() + "]";
    }

    protected abstract MyJList<Integer> filter(Predicate<Integer> predicate);

    protected abstract MyJList<Integer> map(Function<Integer, Integer> function);

    protected abstract MyJList<Integer> flatMap(Function<Integer, MyJList<Integer>> function);

    protected abstract MyJList<Integer> flattern(MyJList<Integer> list);

}

class JEmpty extends MyJList<Integer> {

    private static JEmpty instance;

    private JEmpty() {
    }

    public static JEmpty apply() {
        if (instance == null) {
            instance = new JEmpty();
        }
        return instance;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    protected MyJList<Integer> add(Integer element) {
        return new JCons<Integer>(element, new JEmpty());
    }

    @Override
    protected String printElements() {
        return "";
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    protected MyJList<Integer> filter(Predicate<Integer> predicate) {
        return new JEmpty();
    }

    @Override
    protected MyJList<Integer> map(Function<Integer, Integer> function) {
        return new JEmpty();
    }

    @Override
    protected MyJList<Integer> flatMap(Function<Integer, MyJList<Integer>> function) {
        return new JEmpty();
    }

    @Override
    protected MyJList<Integer> flattern(MyJList<Integer> list) {
        return new JEmpty();
    }
}

class JCons<Integer> extends MyJList<Integer> {

    public JCons(Integer head, MyJList<Integer> tail) {
        this.head = head;
        this.tail = tail;
    }

    public JCons<Integer> apply(Integer head, MyJList<Integer> tail) {
        return new JCons<>(head, tail);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    protected MyJList<Integer> add(Integer element) {
        return new JCons<>(element, this);
    }

    @Override
    protected String printElements() {
        if (tail.isEmpty()) {
            return head.toString();
        } else {
            return head + " " + tail.printElements();
        }
    }

    @Override
    protected MyJList<Integer> filter(Predicate<Integer> predicate) {
        if (predicate.test(head)) {
            return new JCons<>(head, tail.filter(predicate));
        } else {
            return tail.filter(predicate);
        }
    }

    @Override
    protected MyJList<Integer> map(Function<Integer, Integer> function) {
        return new JCons<>(function.apply(head), tail.map(function));
    }

    @Override
    protected MyJList<Integer> flatMap(Function<Integer, MyJList<Integer>> function) {
        //Se calculan todos pero solo estoy devolviendo el primero!
        return function.apply(head).flattern(tail.flatMap(function));
    }

    @Override
    protected MyJList<Integer> flattern(MyJList<Integer> list) {
        return new JCons<>(head, tail.flattern(list));
    }
}

class Test {
    public static void main(String... args) {
        JCons<Integer> listOfInts = new JCons<>(1, new JCons<>(2, new JCons<>(3, JEmpty.apply())));
        System.out.println("listOfInts = " + listOfInts);
        System.out.println("listOfInts = " + listOfInts.filter(i -> i % 2 == 0));
        System.out.println("listOfInts = " + listOfInts.map(i -> i * 42));
        System.out.println("listOfInts = " + listOfInts.flatMap(i -> new JCons<>(i, new JCons<>(i + 1, JEmpty.apply()))));
    }
}