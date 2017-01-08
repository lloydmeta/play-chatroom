package actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import play.api.libs.json.{JsValue, Json}
import play.twirl.api.HtmlFormat

/**
  * Created by Lloyd on 12/6/16.
  *
  * Copyright 2016
  */
object User {
  def props(room: ActorRef, out: ActorRef): Props =
    Props(classOf[User], room, out)
}

class User(room: ActorRef, out: ActorRef) extends Actor with ActorLogging {

  override def preStart(): Unit =
    room ! Subscribe // subscribe to the room on start

  override def postStop(): Unit =
    room ! Unsubscribe // unsubscribe to the room when the actor stops

  def receive: Receive = {
    // A JSON from the websocket connection
    case json: JsValue => {
      json.asOpt[Message] match {
        case Some(msg) => {
          val sanitisedText = sanitise(msg.text)
          room ! msg.copy(sanitisedText)
        }
        case _ => log.error(s"Unanticipated JSON: $json")
      }
    }
    // A message from the chatroom
    case msg: Message => out ! Json.toJson(msg)
  }

  private def sanitise(text: String): String = {
    HtmlFormat.escape(text).toString
  }
}
