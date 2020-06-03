package exercises

abstract class Maybe[+A] {

  def filter(p: A => Boolean): Maybe[A]

  def map[B](f: A => B): Maybe[B]

  def flatMap[B](f: A => Maybe[B]): Maybe[B]
}

case object MaybeNot extends Maybe[Nothing] {

  override def filter(p: Nothing => Boolean): Maybe[Nothing] = MaybeNot

  override def map[B](f: Nothing => B): Maybe[B] = MaybeNot

  override def flatMap[B](f: Nothing => Maybe[B]): Maybe[B] = MaybeNot
}

case class Just[+A](value: A) extends Maybe[A] {

  override def filter(p: A => Boolean): Maybe[A] = if (p.apply(value)) this else MaybeNot

  override def map[B](f: A => B): Maybe[B] = new Just(f.apply(value))

  override def flatMap[B](f: A => Maybe[B]): Maybe[B] = f(value)
}

object MaybeTest extends App {
  val just3 = Just(3)
  println(just3)
  println(just3.map(_ + 2))
  println(just3.flatMap(x => Just(x % 2 == 0)))
  println(just3.filter(_ % 2 == 0))
}
