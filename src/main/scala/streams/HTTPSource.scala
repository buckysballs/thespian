package streams

import akka.actor.{ActorLogging, ActorRef}
import akka.stream.actor.ActorPublisher
import spray.http._


/**
  * Created by @buckysballs on 2/7/17.
  */

/**
  *
  * @tparam T
  */
trait HTTPSource[T] extends ActorPublisher[T] with ActorLogging {
  val uri: Uri
  val io: ActorRef
  val request: HttpRequest
}

/**
  *
  * Reconstructs messages sent with the Chunked transfer encoding.
  */
class ChunkBuilder(delimiter: Char = '\n') {
  private[this] var buffer: String = ""

  def append(chunk: String): Unit = buffer += chunk

  def toIterator: Iterator[String] = {
    new Iterator[String] {
      override def hasNext: Boolean = buffer.indexOf(delimiter) >= 0

      override def next(): String = {
        val index = buffer.indexOf(delimiter)
        val head = buffer.substring(0, index)
        val tail = buffer.substring(index + 1)
        reset(tail)
        head
      }
    }
  }

  def reset(newBuffer: String = ""): Unit = buffer = newBuffer
}
