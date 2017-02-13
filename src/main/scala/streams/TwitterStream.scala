package streams

import akka.actor.{ActorSystem, Props}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import twitterutil.TwitterSource

/**
  * Created by @buckysballs on 2/12/17.
  */
object TwitterStream extends App {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  val src = Source.actorPublisher(Props(new TwitterSource("trump")))
  src.runForeach(println(_))
}
