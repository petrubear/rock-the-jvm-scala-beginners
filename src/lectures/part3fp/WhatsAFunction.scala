package lectures.part3fp

object WhatsAFunction extends App {

  // DREAM: use functions as first class elements
  // problem: oop

  val doubler = new MyFunction[Int, Int] {
    override def apply(element: Int): Int = element * 2
  }

  println(doubler(2))

  // function types = Function1[A, B]
  val stringToIntConverter = new Function1[String, Int] {
    override def apply(string: String): Int = string.toInt
  }

  println(stringToIntConverter("3") + 4)

  val adder: ((Int, Int) => Int) = new Function2[Int, Int, Int] {
    override def apply(a: Int, b: Int): Int = a + b
  }

  trait MyFunction[A, B] {
    def apply(element: A): B
  }

  // Function types Function2[A, B, R] === (A,B) => R

  // ALL SCALA FUNCTIONS ARE OBJECTS

  /*
    1.  a function which takes 2 strings and concatenates them
    2.  transform the MyPredicate and MyTransformer into function types
    3.  define a function which takes an int and returns another function which takes an int and returns an int
        - what's the type of this function
        - how to do it
   */

  def concat: ((String, String) => String) = new Function2[String, String, String] {
    override def apply(s1: String, s2: String): String = s1 + s2
  }

  println(concat("hola", " mundo"))

  def supperAdder: (Int) => ((Int) => Int) = (x: Int) => (y: Int) => x + y

  val adder3 = supperAdder(3)
  println(adder3(4))

  println(supperAdder(3)(4))
}
