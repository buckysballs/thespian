package apps

import twitter.{StartingInfo, TwitterFeedDirector}
import akka.actor.{ActorSystem, Props}

/**
  * Created by @buckysballs on 6/9/16.
  */

object TwitterStatusStreamer {


  def main(args: Array[String]) {
    val system = ActorSystem("TwitterParse")
    val director = system.actorOf(Props(new TwitterFeedDirector(system)), name = "TwitterParse")
    director ! StartingInfo(director, 3)
  }
}