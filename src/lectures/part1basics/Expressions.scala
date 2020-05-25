package lectures.part1basics

object Expressions extends App {

  val x = 1 + 2
  println(x)

  println(1 + 2 + 3)

  println(1 == x)

  println(!(1 == x))

  var aVariable = 2
  aVariable += 3
  println(aVariable)

  //if expression
  val aCondition = true
  val aConditionedValue = if (aCondition) 5 else 3

  println(aConditionedValue)

  val aWeirdValue = (aVariable = 3) //UNIT
  println(aWeirdValue)


  val aCodeBlock = {
    val y = 2
    val z = y + 1

    if (z > 2) "hello" else "goodbye"
  }
}
