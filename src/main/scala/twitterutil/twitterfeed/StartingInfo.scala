package twitterutil.twitterfeed

import akka.actor.ActorRef
/**
  * Created by @buckysballs on 8/6/16.
  */
case class StartingInfo(director: ActorRef, numWorkers: Int)

case class UpdateInfo(workerId: Int)

case class WorkerInfo(system: ActorRef, id: Int)