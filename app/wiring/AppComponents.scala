package wiring

import play.api.ApplicationLoader.Context
import play.api._
import com.softwaremill.macwire._
import controllers.{Assets, Chat}
import router.Routes

class AppComponents(context: Context)
    extends BuiltInComponentsFromContext(context) {

  private implicit def as = actorSystem

  // Controllers
  private lazy val chat = wire[Chat]
  private lazy val assets = wire[Assets]

  // Router
  private lazy val routePrefix: String = "/"
  lazy val router = wire[Routes]

}
