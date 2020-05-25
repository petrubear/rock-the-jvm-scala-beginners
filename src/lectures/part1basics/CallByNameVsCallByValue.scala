package lectures.part1basics

object CallByNameVsCallByValue extends App {

  def callByValue(x: Long): Unit = {
    println(s"by value $x")
    println(s"by value $x")
  }

  def callByName(x: => Long): Unit = {
    println(s"by name $x")
    println(s"by name $x")
  }

  // lo que hace byname es reemplazar lo que llega como parametro en la posicion donde se usa la variable (es lazy)

  callByValue(System.nanoTime())
  callByName(System.nanoTime())

  def infinite(): Int = 1 + infinite()

  def printFirst(x: Int, y: => Int): Unit = println(x)

  //  printFirst(infinite(), 34)
  printFirst(34, infinite())
}
