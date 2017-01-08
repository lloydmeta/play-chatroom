import org.scalatest.{FunSpec, Matchers}
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}

class AppSpec
    extends FunSpec
    with OneServerPerSuiteWithMyComponents
    with Matchers
    with ScalaFutures
    with IntegrationPatience {

  private lazy val ws = components.wsClient


}
