package tryexercice.exo

import org.scalatest._
import play.api.libs.json.{JsArray, JsNumber}

class UserControllerSpec extends FlatSpec
  with GivenWhenThen
  with TryValues
  with Matchers {

  behavior of "UserController"

  it should "fail when it cannot parse the json" in {
    Given("A malformed json")
    val json = JsArray(Seq(JsNumber(1)))

    When("We ask to save the user")
    val triedUser = DefaultUserController.save(json)

    Then("The result is a failure")
    triedUser should be(a[MalformedUserPayload])
  }
}
