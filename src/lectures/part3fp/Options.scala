package lectures.part3fp

import scala.util.Random

object Options extends App {

  val myFirstOption: Option[Int] = Some(4)
  val noOption: Option[Int] = None

  println(myFirstOption)

  //unsafe
  def unsafeMethod(): String = null

  val result = Some(unsafeMethod()) //WRONG!!!
  val optionResult = Option(unsafeMethod()) // OK!

  println(result)
  println(optionResult)

  //we use optuions in chains
  def backupMethod(): String = "A valid Result"

  val chainedResult = Option(unsafeMethod()).orElse(Option(backupMethod()))

  // DESIGN unsafe APIs
  def betterUnsafeMethod(): Option[String] = None

  def betterBackupMethod(): Option[String] = Some("A valid result")

  val betterChainedResult = betterUnsafeMethod() orElse betterBackupMethod()


  // functions on Options
  println(myFirstOption.isEmpty)
  println(myFirstOption.get) // USAFE - DO NOT USE THIS

  // map, flatMap, filter
  println(myFirstOption.map(_ * 2))
  println(myFirstOption.filter(x => x > 10))
  println(myFirstOption.flatMap(x => Option(x * 10)))

  // for-comprehensions
  val config: Map[String, String] = Map(
    // fetched from elsewhere
    "host" -> "176.45.36.1",
    "port" -> "80"
  )

  class Connection {
    def connect = "Connected" // connect to some server
  }

  object Connection {
    val random = new Random(System.nanoTime())

    def apply(host: String, port: String): Option[Connection] =
      if (random.nextBoolean()) Some(new Connection)
      else None
  }


  //  val host = config.get("host")
  //  val port = config.get("port")
  //  var connection = if (host.isEmpty || port.isEmpty) None else {
  //    Connection(host.get, port.get)
  //  }
  //
  //  println(if (connection.isEmpty) "No connection" else connection.get.connect)

  // try to establish a connection, if so - print the connect method
  val host = config.get("host")
  val port = config.get("port")
  /*
    if (h != null)
      if (p != null)
        return Connection.apply(h, p)
    return null
   */
  val connection = host.flatMap(h => port.flatMap(p => Connection.apply(h, p)))
  /*
    if (c != null)
      return c.connect
    return null
   */
  val connectionStatus = connection.map(c => c.connect)
  // if (connectionStatus == null) println(None) else print (Some(connectionstatus.get))
  println(connectionStatus)
  /*
    if (status != null)
      println(status)
   */
  connectionStatus.foreach(println)


  // chained calls
  config.get("host")
    .flatMap(host => config.get("port")
      .flatMap(port => Connection(host, port))
      .map(connection => connection.connect))
    .foreach(println)

  // for-comprehensions
  val forConnectionStatus = for {
    host <- config.get("host")
    port <- config.get("port")
    connection <- Connection(host, port)
  } yield connection.connect
  forConnectionStatus.foreach(println)


  ///////
  val con = host.flatMap(h => port.flatMap(p => Connection(h, p)))
  val conStatus = con.map(c => c.connect).orElse(None)
  println(s"conStatus = ${conStatus}")
  conStatus.foreach(println)

  config.get("host")
    .flatMap(h => config.get("port")
      .flatMap(p => Connection(h, p))
      .map(conn => conn.connect))
    .foreach(e => println(s"chained: ${e}"))

  val forCh = for {
    h <- config.get("host")
    p <- config.get("port")
    c <- Connection(h, p)
  } yield c.connect

  println(s"forCh = ${forCh}")
}
