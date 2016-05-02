package eitherexercice.exo

import java.time.LocalDate

import eitherexercice.dao.DefaultUserRepository
import eitherexercice.models.{User, UserRepository}
import play.api.libs.json.JsValue

sealed trait UserValidationError

case class UserAlreadyExist(id: Long) extends UserValidationError

case class UserTooYoung(age: Int) extends UserValidationError

case class NameTooLong(name: String) extends UserValidationError

case object EmptyUserName extends UserValidationError

case class MalformedUserPayload(json: JsValue) extends UserValidationError

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

  def save(json: JsValue): Either[UserValidationError, User] = ???
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
  def save(user: ParsedUser): Either[UserValidationError, User] = ???
}

object DefaultUserServices extends UserServices(DefaultUserRepository, DefaultCountryServices)
