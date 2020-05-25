package lectures.part1basics

object Functions extends App {
  def aFunction(a: String, b: Int): String = {
    a + " " + b
  }

  println(aFunction("hello", 3))

  def aParameterLessFunction() = 42

  println(aParameterLessFunction())
  println(aParameterLessFunction)


  def aRepeatedFunction(aString: String, n: Int): String = {
    if (n == 1) aString else aString + aRepeatedFunction(aString, n - 1)
  }

  println(aRepeatedFunction("hello", 3))

  def aFunctionWithSideEffects(aString: String): Unit = println(aString)

  def aBigFunction(n: Int) = {
    def aSmallFunction(a: Int, b: Int) = a + b

    aSmallFunction(n, n - 1)
  }

  println(aBigFunction(7))

  //exercise1
  def greetings(name: String, age: Int) = s"Hi, my name is ${name}, and I am ${age} years old"

  println(greetings("Edison", 38))

  //exercise2
  def factorial(n: Int): Int = {
    if (n <= 1) 1 else n * factorial(n - 1)
  }

  println(factorial(8)) //40320

  //excercise3
  def fibonacci(n: Int): Int = {
    if (n <= 2) 1 else fibonacci(n - 1) + fibonacci(n - 2)
  }

  println(fibonacci(8))

  //exercise4
  def isPrime(n: Int): Boolean = {
    def isPrimeUntil(t: Int): Boolean = {
      if (t <= 1) true else n % t != 0 && isPrimeUntil(t - 1)
    }

    isPrimeUntil(n / 2)
  }

  println(isPrime(7))
}
