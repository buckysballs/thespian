package twitterutil.twitterfeed

import akka.actor.{ActorSystem, Props}

/**
  * Created by @buckysballs on 6/9/16.
  */

object TwitterStatusFeed extends App {
  val system = ActorSystem("TwitterParse")
  val director = system.actorOf(Props(new TwitterFeedDirector(system)), name = "TwitterParse")
  director ! StartingInfo(director, 3)
}
