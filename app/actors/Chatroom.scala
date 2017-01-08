package actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.event.LoggingReceive

/**
  * Created by Lloyd on 12/6/16.
  *
  * Copyright 2016
  */
object Chatroom {
  def props: Props = Props[Chatroom]
}

class Chatroom extends Actor with ActorLogging {

  // List of actor addresses
  private var subscribers: Set[ActorRef] = Set.empty[ActorRef]

  def receive: Receive = LoggingReceive {
    case Subscribe => subscribers = subscribers + sender()
    case Unsubscribe => subscribers = subscribers - sender()
    case msg: Message => {
      for {
        s <- subscribers
        if s != sender() // no forwarding to the user who sent it
      } s ! msg
    }

  }
}
