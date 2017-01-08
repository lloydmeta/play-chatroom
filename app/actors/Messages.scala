package actors

import play.api.libs.json.{Json, OFormat}

/**
  * Created by Lloyd on 12/6/16.
  *
  * Copyright 2016
  */
case object Subscribe
case object Unsubscribe

object Message {
  implicit val jsonFormat: OFormat[Message] = Json.format[Message]
}
case class Message(text: String)
