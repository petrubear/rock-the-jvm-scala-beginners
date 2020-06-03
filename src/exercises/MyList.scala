package exercises

import java.util.NoSuchElementException

abstract class MyList[+A] {

  def head: A

  def tail: MyList[A]

  def isEmpty: Boolean

  def add[B >: A](n: B): MyList[B]

  def printElements: String

  override def toString: String = s"[$printElements]"

  def map[B](transformer: A => B): MyList[B]

  def filter(predicate: A => Boolean): MyList[A]

  def flatMap[B](transformer: A => MyList[B]): MyList[B]

  def ++[B >: A](list: MyList[B]): MyList[B]

  def foreach(f: A => Unit)

  def sort(f: (A, A) => Int): MyList[A]

  def zipWith[B, C](list: MyList[B], f: (A, B) => C): MyList[C]

  def fold[B](start: B)(operator: (B, A) => B): B
}

case object Empty extends MyList[Nothing] {
  override def head: Nothing = throw new NoSuchElementException

  override def tail: MyList[Nothing] = throw new NoSuchElementException

  override def isEmpty: Boolean = true

  override def add[B >: Nothing](n: B): MyList[B] = new Cons[B](n, Empty)

  override def printElements: String = ""

  override def map[B](transformer: Nothing => B): MyList[B] = Empty

  override def filter(predicate: Nothing => Boolean): MyList[Nothing] = Empty

  override def flatMap[B](transformer: Nothing => MyList[B]): MyList[B] = Empty

  override def ++[B >: Nothing](list: MyList[B]): MyList[B] = list

  override def foreach(f: Nothing => Unit): Unit = ()

  override def sort(f: (Nothing, Nothing) => Int): MyList[Nothing] = Empty

  override def zipWith[B, C](list: MyList[B], f: (Nothing, B) => C): MyList[C] = {
    if (list.isEmpty) Empty else throw new RuntimeException("List doesnt have the same size")
  }

  override def fold[B](start: B)(operator: (B, Nothing) => B): B = start
}

case class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {
  override def head: A = h

  override def tail: MyList[A] = t

  override def isEmpty: Boolean = false

  override def add[B >: A](n: B): MyList[B] = Cons(n, this)

  override def printElements: String = if (t.isEmpty) s"$h" else s"$h ${t.printElements}"

  override def map[B](transformer: A => B): MyList[B] =
    new Cons[B](transformer.apply(head), tail.map(transformer))

  override def filter(predicate: A => Boolean): MyList[A] =
    if (predicate.apply(head)) new Cons[A](head, tail.filter(predicate)) else tail.filter(predicate)

  override def flatMap[B](transformer: A => MyList[B]): MyList[B] =
    transformer.apply(head) ++ tail.flatMap(transformer)

  override def ++[B >: A](list: MyList[B]): MyList[B] = new Cons[B](head, tail ++ list)

  override def foreach(f: A => Unit): Unit = {
    f(head)
    tail.foreach(f)
  }

  override def sort(f: (A, A) => Int): MyList[A] = {
    def insert(x: A, sortedList: MyList[A]): MyList[A] =
      if (sortedList.isEmpty) new Cons(x, Empty)
      else if (f(x, sortedList.head) <= 0) new Cons(x, sortedList)
      else new Cons(sortedList.head, insert(x, sortedList.tail))

    val sortedTail = t.sort(f)
    insert(h, sortedTail)
  }

  override def zipWith[B, C](list: MyList[B], f: (A, B) => C): MyList[C] =
    if (list.isEmpty) throw new RuntimeException("Lists do not have the same length")
    else Cons(f(h, list.head), t.zipWith(list.tail, f))

  override def fold[B](start: B)(operator: (B, A) => B): B =
    t.fold(operator(start, h))(operator)
}

object ListTest extends App {
  var listOfInts = new Cons[Int](1, new Cons[Int](2, new Cons[Int](3, Empty)))
  var cloneListOfInts = new Cons[Int](1, new Cons[Int](2, new Cons[Int](3, Empty)))
  var newListOfInts = new Cons[Int](4, new Cons[Int](5, new Cons[Int](6, Empty)))
  var listOfStrings = new Cons[String]("a", new Cons[String]("b", new Cons[String]("c", Empty)))
  println(listOfInts.toString)
  println(listOfStrings.toString)
  println(listOfInts.map(element => element + 2))
  println(listOfInts.map(_ + 2))
  println(listOfInts.filter(element => element % 2 == 0))
  println(listOfInts ++ newListOfInts)
  println(listOfInts.flatMap(element => Cons(element, Cons(element + 1, Empty))))
  println(listOfInts == cloneListOfInts)

  println(s"foreach: $listOfInts")
  listOfInts.foreach((x: Int) => println(x + 10))
  println("Sort")
  println(listOfInts.sort((x, y) => y - x))
  println("Zip")
  println(listOfInts.zipWith(newListOfInts, (x: Int, y: Int) => x * y))
  println(listOfInts.zipWith(newListOfInts, (x: Int, y: Int) => s"$x + $y = ${x + y};"))
  println("Fold")
  println(listOfInts.fold(0)(_ + _))

  // for comprehensions
  val combinations = for {
    n <- listOfInts
    string <- listOfStrings
  } yield n + "-" + string
  println(combinations)
}

