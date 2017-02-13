package twitterutil

import spray.httpx.unmarshalling.{Deserialized, MalformedContent}
import spray.json.{JsNull, JsNumber, JsObject, JsString, JsValue, JsonParser}

import scala.util.Try

/**
  * Created by @buckysballs on 2/12/17.
  */
trait TweetMarshaller {
  object TweetUnmarshaller {

    def apply(entityString: String, query: String): Deserialized[Tweet] = {
      Try {
        val json = JsonParser(entityString).asJsObject
        (json.fields.get("id_str"), json.fields.get("text"), json.fields.get("place"), json.fields.get("user")) match {
          case (Some(JsString(id)), Some(JsString(text)), Some(place), Some(user: JsObject)) =>
            val x = mkUser(user).fold(x => Left(x), { user =>
              mkPlace(place).fold(x => Left(x), { place =>
                val jsonString = json.copy(Map("query" -> JsString(query)) ++ json.fields).compactPrint
                Right(Tweet(id, user, text, place, jsonString))
              })
            })
            x
          case _ => Left(MalformedContent("bad tweet"))
        }
      }
    }.getOrElse(Left(MalformedContent("bad json")))

    def mkUser(user: JsObject): Deserialized[User] = {
      (user.fields("id_str"), user.fields("lang"), user.fields("followers_count")) match {
        case (JsString(id), JsString(lang), JsNumber(followers)) => Right(User(id, lang, followers.toInt))
        case (JsString(id), _, _) => Right(User(id, "", 0))
        case _ => Left(MalformedContent("bad user"))
      }
    }

    def mkPlace(place: JsValue): Deserialized[Option[Place]] = place match {
      case JsObject(fields) =>
        (fields.get("country"), fields.get("name")) match {
          case (Some(JsString(country)), Some(JsString(name))) => Right(Some(Place(country, name)))
          case _ => Left(MalformedContent("bad place"))
        }
      case JsNull => Right(None)
      case _ => Left(MalformedContent("bad tweet"))
    }
  }
}