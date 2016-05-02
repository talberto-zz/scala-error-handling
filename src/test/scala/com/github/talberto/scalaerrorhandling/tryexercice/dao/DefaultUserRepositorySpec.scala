package com.github.talberto.scalaerrorhandling.tryexercice.dao

import com.github.talberto.scalaerrorhandling.tryexercice.UserFixtures
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}

class DefaultUserRepositorySpec extends FlatSpec
  with GivenWhenThen
  with Matchers {

  behavior of "DefaultUserRepository"

  it should "retrieve a user after inserting it" in {
    Given("A new user is saved into the repository")
    val user = UserFixtures.validUser

    DefaultUserRepository.save(user)

    When("We retrieve the user with the same id")
    val retrievedUser = DefaultUserRepository.find(user.id)

    Then("The saved user and the retrieved one are the same")
    user should equal(retrievedUser)
  }

}
