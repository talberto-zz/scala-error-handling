package com.github.talberto.scalaerrorhandling

import java.time.LocalDate

import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}

class InMemoryUserJpaRepositorySpec extends FlatSpec
  with GivenWhenThen
  with Matchers {

  behavior of "InMemoryUserJpaRepositorySpec"

  it should "retrieve a user after inserting it" in {
    Given("A new user is saved into the repository")
    val contactInfo = ContactInfo(
      country = Country("FR", "France"),
      city = "Paris",
      street = "Rue de Rivoli",
      postalCode = "75004"
    )
    val user = User(
      id = 1005L,
      firstName = "John",
      lastName = "Doe",
      birthDate = LocalDate.parse("1953-03-05"),
      contactInfo = contactInfo
    )

    InMemoryUserJpaRepository.save(user)

    When("We retrieve the user with the same id")
    val retrievedUser = InMemoryUserJpaRepository.find(user.id)

    Then("The saved user and the retrieved one are the same")
    user should equal(retrievedUser)
  }

}
