package eitherexercice.exo

import eitherexercice.dao.DefaultCountryRepository
import eitherexercice.models.{Country, CountryRepository}
import play.api.libs.json.JsValue

sealed trait CountryValidationError

case class CountryAlreadyExists(isoCode: String) extends CountryValidationError

case class CountryNotFound(isoCode: String) extends CountryValidationError

case class ParsedCountry(
                          countryIsoCode: String
                        )

case class CountryController(countryServices: CountryServices) {

  def find(isoCode: String): Option[Country] = countryServices.find(isoCode)

  def findAll(): Seq[Country] = countryServices.findAll()

  def save(json: JsValue): Either[CountryValidationError, Country] = ???
}

case class CountryServices(countryRepository: CountryRepository) {

  def find(isoCode: String): Option[Country] = ???

  def findAll(): Seq[Country] = ???

  def save(country: ParsedCountry): Either[CountryValidationError, Country] = ???

}

object DefaultCountryServices extends CountryServices(DefaultCountryRepository)