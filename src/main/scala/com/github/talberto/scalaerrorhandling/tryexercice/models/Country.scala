package com.github.talberto.scalaerrorhandling.tryexercice.models

import java.util.Locale

object CountryConstraints {

  val IsoCountries = {
    val locales = Locale.getISOCountries.to[Seq].map(new Locale("", _))

    for {
      locale <- locales
    } yield {
      Country(
        isoCode = locale.getCountry,
        name = locale.getDisplayCountry()
      )
    }
  }

}

case class Country(
                    isoCode: String,
                    name: String
                  )

trait CountryRepository {
  /**
    * Finds and returns a country given it's id. Null if user is not found
    */
  def find(isoCode: String): Country

  /**
    * Returns all the countries in the repository
    */
  def findAll(): Seq[Country]

  /**
    * Stores a new country into the repository. It performs some validations before inserting,
    * if a validation doesn't pass, it throws an exception
    */
  def save(country: Country): Unit
}

case class CountryAlreadyExistsException(isoCode: String) extends RuntimeException(s"There already exist a country with iso code [$isoCode]")

case class CountryNotFoundException(isoCode: String) extends RuntimeException(s"There doesn't exist a country with iso code [$isoCode]")