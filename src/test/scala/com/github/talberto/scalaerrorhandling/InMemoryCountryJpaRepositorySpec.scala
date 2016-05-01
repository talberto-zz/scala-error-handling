package com.github.talberto.scalaerrorhandling

import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}

class InMemoryCountryJpaRepositorySpec extends FlatSpec
  with GivenWhenThen
  with Matchers {

  behavior of "InMemoryCountryJpaRepositorySpec"

  it should "retrieve a country after inserting it" in {
    Given("A new country is saved into the repository")
    val country = Country(
      isoCode = "WD",
      name = "Wonderland"
    )

    InMemoryCountryJpaRepository.save(country)

    When("We retrieve the country with the same iso code")
    val retrievedCountry = InMemoryCountryJpaRepository.find(country.isoCode)

    Then("The saved country and the retrieved one are the same")
    country should equal(retrievedCountry)
  }

}
