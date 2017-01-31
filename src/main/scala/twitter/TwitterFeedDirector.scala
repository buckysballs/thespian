package twitter

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

/**
  * Created by @buckysballs on 8/6/16.
  */
class TwitterFeedDirector(system: ActorSystem) extends Actor {

  var workers = Map[Int, ActorRef]()

  val tweetCount = scala.collection.mutable.Map[Int, Int]()

  def receive = {
    case StartingInfo(directorRef, workerCount) =>
      workers = startWorkers(directorRef, workerCount)
      workers.foreach { case (id, actorRef) => actorRef ! WorkerInfo(actorRef, id) }

    case UpdateInfo(workerId) =>
      tweetCount(workerId) = tweetCount.getOrElse(workerId, 0) + 1
      println(tweetCount)
  }

  def startWorkers(directorRef: ActorRef, numWorkers: Int): Map[Int, ActorRef] = {
    (0 until numWorkers).map(workerId =>
      (workerId, system.actorOf(Props(new TwitterFeedActor(directorRef, workerId)), name = workerId.toString))).toMap
  }

}
