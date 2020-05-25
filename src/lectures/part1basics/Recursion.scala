package lectures.part1basics

import scala.annotation.tailrec

object Recursion extends App {
  //throws stackoverflow for big numbers
  def factorial(n: Int): Int = {
    if (n <= 1) 1 else {
      println(s"Calculating factorial for ${n}")
      n * factorial(n - 1)
    }
  }

  println(factorial(10))

  // evita el stackoverflow (tail recursive)
  def anotherFactorial(n: Int): Int = {
    @tailrec
    def factorialHelper(x: Int, accumulator: Int): Int = {
      if (x < 1) accumulator else
        factorialHelper(x - 1, x * accumulator)
    }

    factorialHelper(n, 1)
  }

  println(anotherFactorial(10))


  //ex 1
  def aRepeatedFunction(aString: String, n: Int): String = {
    @scala.annotation.tailrec
    def repeat(aString: String, times: Int, accumulator: String): String = {
      if (times == 1) accumulator else repeat(aString, times - 1, aString + accumulator)
    }

    repeat(aString, n, aString)
  }

  println(aRepeatedFunction("hello", 5))

  //ex2
  def isPrime(n: Int): Boolean = {
    @tailrec
    def isPrimeTailrec(t: Int, isStillPrime: Boolean): Boolean =
      if (!isStillPrime) false
      else if (t <= 1) true
      else isPrimeTailrec(t - 1, n % t != 0 && isStillPrime)

    isPrimeTailrec(n / 2, true)
  }

  println(isPrime(2003))
  println(isPrime(629))

  def fibonacci(n: Int): Int = {
    @scala.annotation.tailrec
    def fiboTailrec(i: Int, last: Int, nextToLast: Int): Int =
      if (i >= n) last
      else fiboTailrec(i + 1, last + nextToLast, last)

    if (n <= 2) 1
    else fiboTailrec(2, 1, 1)
  }

  println(fibonacci(8)) // 1 1 2 3 5 8 13, 21
}
