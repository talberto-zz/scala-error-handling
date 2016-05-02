package eitherexercice.exo

import java.time.LocalDate

import eitherexercice.dao.InMemoryUserRepository
import eitherexercice.models._
import org.scalatest._

import scala.collection.mutable
import scala.util.Random

class UserServicesSpec extends FlatSpec
  with GivenWhenThen
  with OptionValues
  with EitherValues
  with Matchers {

  behavior of "UserServices"

  it should "return none when the user doesn't exist" in {
    Given("A nonexisting user")
    val userId = 1000L

    When("We ask to retrieve it")
    val userServices = DefaultUserServices
    val maybeUser = userServices.find(userId)

    Then("The result is None")
    maybeUser should be(None)
  }

  it should "return some user when the user exist" in {
    Given("An existing user")
    val user = eitherexercice.UserFixtures.validUser
    val userServices = UserServices(
      InMemoryUserRepository(mutable.Map(user.id -> user)),
      DefaultCountryServices
    )

    When("We ask to retrieve it")
    val maybeUser = userServices.find(user.id)

    Then("The result is some user")
    maybeUser.value should equal(user)
  }

  it should "return a user when saving a valid parsed user" in {
    Given("A valid parsed user")
    val parsedUser = eitherexercice.ParsedUserFixtures.validParsedUser

    When("Saving the parsed user")
    val triedUser = DefaultUserServices.save(parsedUser)

    Then("We get a user")
    triedUser.right.value.name should be(parsedUser.name)
    triedUser.right.value.birthDate should be(parsedUser.birthDate)
    triedUser.right.value.contactInfo.country.isoCode should be(parsedUser.contactInfo.countryIsoCode)
    triedUser.right.value.contactInfo.city should be(parsedUser.contactInfo.city)
  }

  it should "fail when saving a user with a name too long" in {
    Given("A user with a name too long")
    val parsedUser = eitherexercice.ParsedUserFixtures.validParsedUser.copy(name = Random.nextString(UserConstraints.MaxNameLength + 10))

    When("Saving the parsed user")
    val triedUser = DefaultUserServices.save(parsedUser)

    Then("We get a failure")
    triedUser.left.value should be(a[NameTooLong])
  }

  it should "fail when saving a user with an empty name" in {
    Given("A user with a name too long")
    val parsedUser = eitherexercice.ParsedUserFixtures.validParsedUser.copy(name = "")

    When("Saving the parsed user")
    val triedUser = DefaultUserServices.save(parsedUser)

    Then("We get a failure")
    triedUser.left.value should be(EmptyUserName)
  }

  it should "fail when saving a user too young" in {
    Given("A user with a name too long")
    val parsedUser = eitherexercice.ParsedUserFixtures.validParsedUser.copy(birthDate = LocalDate.now)

    When("Saving the parsed user")
    val triedUser = DefaultUserServices.save(parsedUser)

    Then("We get a failure")
    triedUser.left.value should be(a[UserTooYoung])
  }

  it should "fail when saving a user with an non existent country" in {
    Given("A user with a name too long")
    val parsedContactInfo = eitherexercice.ParsedContactInfoFixtures.validContactInfo.copy(countryIsoCode = "ZZZZ")
    val parsedUser = eitherexercice.ParsedUserFixtures.validParsedUser.copy(contactInfo = parsedContactInfo)

    When("Saving the parsed user")
    val triedUser = DefaultUserServices.save(parsedUser)

    Then("We get a failure")
    triedUser.left.value should be(a[CountryNotFound])
  }
}
