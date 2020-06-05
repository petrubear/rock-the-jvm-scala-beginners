package lectures.part3fp

import scala.annotation.tailrec

object TuplesAndMaps extends App {

  // tuples = finite ordered "lists"
  val aTuple = (2, "hello, Scala") // Tuple2[Int, String] = (Int, String)

  println(aTuple._1) // 2
  println(aTuple.copy(_2 = "goodbye Java"))
  println(aTuple.swap) // ("hello, Scala", 2)

  // Maps - keys -> values
  val aMap: Map[String, Int] = Map()

  val phonebook = Map(("Jim", 555), "Daniel" -> 789, ("JIM", 9000)).withDefaultValue(-1)
  // a -> b is sugar for (a, b)
  println(phonebook)

  // map ops
  println(phonebook.contains("Jim"))
  println(phonebook("Mary"))

  // add a pairing
  val newPairing = "Mary" -> 678
  val newPhonebook = phonebook + newPairing
  println(newPhonebook)

  // functionals on maps
  // map, flatMap, filter
  println(phonebook.map(pair => pair._1.toLowerCase -> pair._2))

  // filterKeys
  println(phonebook.filterKeys(x => x.startsWith("J")))
  // mapValues
  println(phonebook.mapValues(number => "0245-" + number))

  // conversions to other collections
  println(phonebook.toList)
  println(List(("Daniel", 555)).toMap)
  val names = List("Bob", "James", "Angela", "Mary", "Daniel", "Jim")
  println(names.groupBy(name => name.charAt(0)))

  /*
    1.  What would happen if I had two original entries "Jim" -> 555 and "JIM" -> 900
        !!! careful with mapping keys.
    2.  Overly simplified social network based on maps
        Person = String
        - add a person to the network
        - remove
        - friend (mutual)
        - unfriend
        - number of friends of a person
        - person with most friends
        - how many people have NO friends
        - if there is a social connection between two people (direct or not)
   */


  //1
  val test1 = Map(("JIM", 1), ("jim", 2))
  println(test1.map(tuple => (tuple._1.toLowerCase, tuple._2)))

  //2


  def my_add(network: Map[String, Set[String]], person: String) = {
    network + (person -> Set[String]())
  }

  def my_remove(network: Map[String, Set[String]], person: String) = {
    network - person
  }

  def my_friend(network: Map[String, Set[String]], person: String, friend: String) = {
    network + (person -> (network(person) + friend)) + (friend -> (network(friend) + person))
  }

  def my_unfriend(network: Map[String, Set[String]], person: String, friend: String) = {
    network + (person -> (network(person) - friend)) + (friend -> (network(friend) - person))
  }

  def my_friends(network: Map[String, Set[String]], person: String): Int = {
    if (network.contains(person)) network(person).size else 0
  }

  def my_mostFriends(network: Map[String, Set[String]]) = {
    network.maxBy(tuple => tuple._2.size)._1
  }

  def my_noFriends(network: Map[String, Set[String]]) = {
    network.filter(tuple => tuple._2.isEmpty).keys
  }


  //tests
  val my_empty: Map[String, Set[String]] = Map()
  private val p1 = "Edison"
  private val p2 = "Luna"
  private val p3 = "Vale"
  var my_network = my_add(my_empty, p1)
  println(my_network)
  val removed = my_remove(my_network, p1)
  println(removed)

  my_network = my_add(my_empty, p1)
  my_network = my_add(my_network, p2)
  my_network = my_friend(my_network, p1, p2)
  println(my_network)

  my_network = my_add(my_network, p3)
  my_network = my_friend(my_network, p1, p3)
  println(my_network)
  my_network = my_unfriend(my_network, p1, p2)
  println(my_network)
  my_network = my_friend(my_network, p1, p2)
  println(my_network)
  println(my_friends(my_network, "er"))
  println(my_friends(my_network, p1))

  my_network = add(my_network, "Pam")
  println(my_network)
  println(mostFriends(my_network))
  println(my_noFriends(my_network))

  //instructor
  def add(network: Map[String, Set[String]], person: String): Map[String, Set[String]] =
    network + (person -> Set())

  def friend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] = {
    val friendsA = network(a)
    val friendsB = network(b)

    network + (a -> (friendsA + b)) + (b -> (friendsB + a))
  }

  def unfriend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] = {
    val friendsA = network(a)
    val friendsB = network(b)

    network + (a -> (friendsA - b)) + (b -> (friendsB - a))
  }

  def remove(network: Map[String, Set[String]], person: String): Map[String, Set[String]] = {
    def removeAux(friends: Set[String], networkAcc: Map[String, Set[String]]): Map[String, Set[String]] =
      if (friends.isEmpty) networkAcc
      else removeAux(friends.tail, unfriend(networkAcc, person, friends.head))

    val unfriended = removeAux(network(person), network)
    unfriended - person
  }

  val empty: Map[String, Set[String]] = Map()
  val network = add(add(empty, "Bob"), "Mary")
  println(network)
  println(friend(network, "Bob", "Mary"))
  println(unfriend(friend(network, "Bob", "Mary"), "Bob", "Mary"))
  println(remove(friend(network, "Bob", "Mary"), "Bob"))

  // Jim,Bob,Mary
  val people = add(add(add(empty, "Bob"), "Mary"), "Jim")
  val jimBob = friend(people, "Bob", "Jim")
  val testNet = friend(jimBob, "Bob", "Mary")

  println(testNet)

  def nFriends(network: Map[String, Set[String]], person: String): Int =
    if (!network.contains(person)) 0
    else network(person).size

  println(nFriends(testNet, "Bob"))

  def mostFriends(network: Map[String, Set[String]]): String =
    network.maxBy(pair => pair._2.size)._1

  println(mostFriends(testNet))

  def nPeopleWithNoFriends(network: Map[String, Set[String]]): Int =
    network.count(_._2.isEmpty)

  println(nPeopleWithNoFriends(testNet))

  def socialConnection(network: Map[String, Set[String]], a: String, b: String): Boolean = {
    @tailrec
    def bfs(target: String, consideredPeople: Set[String], discoveredPeople: Set[String]): Boolean = {
      if (discoveredPeople.isEmpty) false
      else {
        val person = discoveredPeople.head
        if (person == target) true
        else if (consideredPeople.contains(person)) bfs(target, consideredPeople, discoveredPeople.tail)
        else bfs(target, consideredPeople + person, discoveredPeople.tail ++ network(person))
      }
    }

    bfs(b, Set(), network(a) + a)
  }

  println(socialConnection(testNet, "Mary", "Jim"))
  println(socialConnection(network, "Mary", "Bob"))

}
