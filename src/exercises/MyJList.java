package exercises;

import java.util.function.Function;
import java.util.function.Predicate;

public abstract class MyJList<T> {
    protected T head;
    protected MyJList<T> tail;

    protected abstract boolean isEmpty();

    protected abstract MyJList<? super T> add(T element);

    protected abstract String printElements();

    @Override
    public String toString() {
        return "[" + printElements() + "]";
    }

    protected abstract MyJList<T> filter(Predicate<T> predicate);

    protected abstract <A> MyJList<A> map(Function<T, A> function);

    protected abstract <A> MyJList<A> flatMap(Function<T, MyJList<A>> function);

    protected abstract MyJList<T> flattern(MyJList<T> list);

}

class JEmpty<T> extends MyJList<Object> {

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
    protected MyJList<Object> add(Object element) {
        return new JCons<>(element, JEmpty.apply());
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
    protected MyJList<Object> filter(Predicate<Object> predicate) {
        return JEmpty.apply();
    }

    @Override
    protected <A> MyJList<A> map(Function<Object, A> function) {
        return JEmpty.apply();
    }

    @Override
    protected <A> MyJList<A> flatMap(Function<Object, MyJList<A>> function) {
        return JEmpty.apply();
    }

    @Override
    protected MyJList<Object> flattern(MyJList<Object> list) {
        return JEmpty.apply();
    }
}

class JCons<T> extends MyJList<T> {

    public JCons(T head, MyJList<T> tail) {
        this.head = head;
        this.tail = tail;
    }

    public JCons<T> apply(T head, MyJList<T> tail) {
        return new JCons<>(head, tail);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    protected MyJList<? super T> add(T element) {
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
    protected MyJList<T> filter(Predicate<T> predicate) {
        if (predicate.test(head)) {
            return new JCons<>(head, tail.filter(predicate));
        } else {
            return tail.filter(predicate);
        }
    }

    @Override
    protected <A> MyJList<A> map(Function<T, A> function) {
        return new JCons<A>(function.apply(head), tail.map(function));
    }

    @Override
    protected <A> MyJList<A> flatMap(Function<T, MyJList<A>> function) {
        //Se calculan todos pero solo estoy devolviendo el primero!
        return function.apply(head).flattern(tail.flatMap(function));
    }

    @Override
    protected MyJList<T> flattern(MyJList<T> list) {
        return new JCons<>(head, tail.flattern(list));
    }
}

class Test {
    public static void main(String... args) {
        JCons<Integer> listOfInts = new JCons<Integer>(1, new JCons<Integer>(2, new JCons<Integer>(3, JEmpty.apply())));
        System.out.println("listOfInts = " + listOfInts);
        System.out.println("listOfInts = " + listOfInts.filter(i -> i % 2 == 0));
        System.out.println("listOfInts = " + listOfInts.map(i -> i * 42));
        System.out.println("listOfInts = " + listOfInts.flatMap(i -> new JCons<>(i, new JCons<>(i + 1, JEmpty.apply()))));
        JCons<String> listOfStrings = new JCons<String>("a", new JCons<String>("b", new JCons<String>("c", JEmpty.apply())));
        System.out.println("listOfStrings = " + listOfStrings);
        System.out.println("listOfStrings = " + listOfStrings.filter(s -> s.equals("b")));
        System.out.println("listOfStrings = " + listOfStrings.map(s -> s.concat("__")));
    }
}