package exercises

abstract class MyList {

  def head: Int

  def tail: MyList

  def isEmpty: Boolean

  def add(n: Int): MyList

  def printElements: String

  override def toString: String = s"[$printElements]"

}

object Empty extends MyList {
  override def head: Int = throw new NoSuchElementException

  override def tail: MyList = throw new NoSuchElementException

  override def isEmpty: Boolean = true

  override def add(n: Int): MyList = new Cons(n, Empty).add(n)

  override def printElements: String = ""
}

class Cons(h: Int, t: MyList) extends MyList {
  override def head: Int = h

  override def tail: MyList = t

  override def isEmpty: Boolean = false

  override def add(n: Int): MyList = new Cons(n, this)

  override def printElements: String = if (t.isEmpty) s"$h" else s"$h ${t.printElements}"
}

object ListTest extends App {
  var list = new Cons(1, new Cons(2, new Cons(3, Empty)))
  println(list.tail.head)
  println(list.add(4).head)
  println(list.toString)
}

