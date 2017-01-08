package controllers

import actors.{Chatroom, User}
import akka.actor.{ActorRef, ActorSystem}
import akka.stream.Materializer
import play.api.libs.json.JsValue
import play.api.libs.streams.ActorFlow
import play.api.mvc.{Action, Controller, WebSocket}

/**
  * Created by Lloyd on 12/6/16.
  *
  * Copyright 2016
  */
class Chat(implicit actorSystem: ActorSystem, materializer: Materializer)
    extends Controller {

  // long-lived chatroom
  private val chatRoom: ActorRef = actorSystem.actorOf(Chatroom.props)

  def index = Action { implicit req =>
    Ok(views.html.index())
  }

  def chat = WebSocket.accept[JsValue, JsValue] { _ =>
    // instantiate a User actor and pass it the chatroom
    ActorFlow.actorRef(out => User.props(chatRoom, out))
  }

}
