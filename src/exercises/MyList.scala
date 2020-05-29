package exercises

abstract class MyList[+A] {

  def head: A

  def tail: MyList[A]

  def isEmpty: Boolean

  def add[B >: A](n: B): MyList[B]

  def printElements: String

  override def toString: String = s"[$printElements]"

}

object Empty extends MyList[Nothing] {
  override def head: Nothing = throw new NoSuchElementException

  override def tail: MyList[Nothing] = throw new NoSuchElementException

  override def isEmpty: Boolean = true

  override def add[B >: Nothing](n: B): MyList[B] = new Cons[B](n, Empty)

  override def printElements: String = ""
}

class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {
  override def head: A = h

  override def tail: MyList[A] = t

  override def isEmpty: Boolean = false

  override def add[B >: A](n: B): MyList[B] = new Cons(n, this)

  override def printElements: String = if (t.isEmpty) s"$h" else s"$h ${t.printElements}"
}

object ListTest extends App {
  var listOfInts = new Cons[Int](1, new Cons[Int](2, new Cons[Int](3, Empty)))
  var listOfStrings = new Cons[String]("a", new Cons[String]("b", new Cons[String]("c", Empty)))
  println(listOfInts.toString)
  println(listOfStrings.toString)
}

