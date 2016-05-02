package com.github.talberto.scalaerrorhandling.tryexercice.dao

import com.github.talberto.scalaerrorhandling.tryexercice.models.Country
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}

class DefaultCountryRepositorySpec extends FlatSpec
  with GivenWhenThen
  with Matchers {

  behavior of "DefaultCountryRepository"

  it should "retrieve a country after inserting it" in {
    Given("A new country is saved into the repository")
    val country = Country(
      isoCode = "WD",
      name = "Wonderland"
    )

    DefaultCountryRepository.save(country)

    When("We retrieve the country with the same iso code")
    val retrievedCountry = DefaultCountryRepository.find(country.isoCode)

    Then("The saved country and the retrieved one are the same")
    country should equal(retrievedCountry)
  }

}
