package com.github.talberto.scalaerrorhandling

import java.util.Locale

import scala.collection.mutable

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

trait CountryJpaRepository {
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

class InMemoryCountryJpaRepository(private val countries: mutable.Map[String, Country]) extends CountryJpaRepository {

  override def find(isoCode: String): Country = {
    countries.getOrElse(isoCode, null)
  }

  override def findAll(): Seq[Country] = {
    countries.values.to[Seq]
  }

  override def save(country: Country): Unit = {
    if (countries.contains(country.isoCode)) {
      throw CountryAlreadyExistsException(country.isoCode)
    }

    countries(country.isoCode) = country
  }
}

object InMemoryCountryJpaRepository extends InMemoryCountryJpaRepository(mutable.Map[String, Country](CountryConstraints.IsoCountries.map(c => c.isoCode -> c): _*))

case class CountryAlreadyExistsException(isoCode: String) extends RuntimeException(s"There already exist a country with iso code [$isoCode]")