package eitherexercice.dao

import eitherexercice.models.{Country, CountryAlreadyExistsException, CountryConstraints, CountryRepository}

import scala.collection.mutable

case class InMemoryCountryRepository(private val countries: mutable.Map[String, Country]) extends CountryRepository {

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

object DefaultCountryRepository extends InMemoryCountryRepository(mutable.Map[String, Country](CountryConstraints.IsoCountries.map(c => c.isoCode -> c): _*))
