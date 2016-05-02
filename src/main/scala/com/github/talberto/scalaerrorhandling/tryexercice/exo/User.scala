package com.github.talberto.scalaerrorhandling.tryexercice.exo

import java.time.LocalDate

import com.github.talberto.scalaerrorhandling.tryexercice.dao.DefaultUserRepository
import com.github.talberto.scalaerrorhandling.tryexercice.models.{User, UserRepository}
import play.api.libs.json.JsValue

import scala.util.Try

case class ParsedContactInfo(
                              countryIsoCode: String,
                              city: String
                            )

case class ParsedUser(
                       name: String,
                       birthDate: LocalDate,
                       contactInfo: ParsedContactInfo
                     )

/**
  * models.User controller, it's responsability is to parse the incoming data and forward the request to the service layer
  */
case class UserController(userServices: UserServices) {

  def find(id: Long): Option[User] = userServices.find(id)

  def findAll(): Seq[User] = userServices.findAll()

  def save(json: JsValue): Try[User] = {
    val parsedUser: ParsedUser = parseUser(json)
    userServices.save(parsedUser)
  }

  private def parseUser(json: JsValue): ParsedUser = ???
}

object DefaultUserController extends UserController(DefaultUserServices)

case class UserServices(userRepository: UserRepository, countryServices: CountryServices) {

  /**
    * Tries to find a user in the system given it's id. If the user is found it returns Some(user), otherwise it
    * returns None
    */
  def find(id: Long): Option[User] = ???

  /**
    * Retrieves all the user in the system
    */
  def findAll(): Seq[User] = ???

  /**
    * Tries to save a new ParsedUser. If it succeeded it returns Success(user), otherwise it
    * returns Failure with an exception denoting the error
    *
    * @param user
    * @return
    */
  def save(user: ParsedUser): Try[User] = ???
}

object DefaultUserServices extends UserServices(DefaultUserRepository, DefaultCountryServices)

case class MalformedUserPayload(json: JsValue) extends RuntimeException(s"The json payload [$json] cannot be parsed into a valid user")