package twitterutil.twitterfeed

import akka.actor.{Actor, ActorRef}
import com.typesafe.scalalogging.LazyLogging
import org.slf4j.LoggerFactory
import org.json4s.DefaultFormats
import twitter4j._
import twitterutil.Config

/**
  * Created by @buckysballs on 8/6/16.
  */

class TwitterFeedActor(system: ActorRef, workerId: Int) extends Actor with LazyLogging {

  implicit val formats = DefaultFormats

  lazy val twitter4jListener = new StatusListener {
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

  val twitterStream: TwitterStream = {
    val instance = Config.twitterSpout.getInstance()
    instance.addListener(twitter4jListener)
    instance
  }


  def receive = {
    case WorkerInfo(_, 1) => twitterStream.filter(new FilterQuery(Array(5988062)))
    case WorkerInfo(_, 2) => twitterStream.filter(new FilterQuery(Array(3108351)))
    case _ => logger.info("Bad Message")
  }

  def updateMaster(parentRef: ActorRef, workerId: Int) = {
    parentRef ! UpdateInfo(workerId)
  }
}
