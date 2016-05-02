package tryexercice.exo

import tryexercice.dao.DefaultCountryRepository
import tryexercice.models.{Country, CountryRepository}
import play.api.libs.json.JsValue

import scala.util.Try

case class ParsedCountry(
                          countryIsoCode: String
                        )

case class CountryController(countryServices: CountryServices) {

  def find(isoCode: String): Option[Country] = countryServices.find(isoCode)

  def findAll(): Seq[Country] = countryServices.findAll()

  def save(json: JsValue): Try[Country] = {
    val parsedCountry: ParsedCountry = parseCountry(json)
    countryServices.save(parsedCountry)
  }

  private def parseCountry(json: JsValue): ParsedCountry = ???
}

case class CountryServices(countryRepository: CountryRepository) {

  def find(isoCode: String): Option[Country] = ???

  def findAll(): Seq[Country] = ???

  def save(country: ParsedCountry): Try[Country] = ???

}

object DefaultCountryServices extends CountryServices(DefaultCountryRepository)