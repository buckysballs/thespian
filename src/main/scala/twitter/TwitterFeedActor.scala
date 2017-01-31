package twitter

import akka.actor.{Actor, ActorRef}
import org.json4s.DefaultFormats
import twitter4j._


/**
  * Created by @buckysballs on 8/6/16.
  */

class TwitterFeedActor(system: ActorRef, workerId: Int) extends Actor {

  implicit val formats = DefaultFormats

  lazy val newSimpleListener = new StatusListener {
    def onStatus(status: Status) {
      updateMaster(system, workerId)
    }

    def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {}

    def onTrackLimitationNotice(numberOfLimitedStatuses: Int) {}

    def onException(ex: Exception) {
      ex.printStackTrace()
    }

    def onScrubGeo(arg0: Long, arg1: Long) {}

    def onStallWarning(warning: StallWarning) {}
  }

  lazy val twitterStream = new TwitterStreamFactory(util.twitterConfig).getInstance
  twitterStream.addListener(newSimpleListener)


  def receive = {

    case WorkerInfo(parentRef, 1) => twitterStream.filter(new FilterQuery(Array(5988062)))
    case WorkerInfo(parentRef, 2) => twitterStream.filter(new FilterQuery(Array(3108351)))
    case _ =>
      println("huh?")
  }

  def updateMaster(parentRef: ActorRef, workerId: Int) = {
    parentRef ! UpdateInfo(workerId)
  }
}
