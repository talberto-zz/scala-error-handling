package com.github.talberto.scalaerrorhandling.tryexercice.models

import java.time.LocalDate

case class ContactInfo(
                        country: Country,
                        city: String
                      )

object UserConstraints {
  val MinAge = 18

  val MaxNameLength = 20
}

case class User(
                 id: Long,
                 name: String,
                 birthDate: LocalDate,
                 contactInfo: ContactInfo
               )

/**
  * Typical JPA like Repository to store users into a persistent database
  */
trait UserRepository {

  /**
    * Finds and returns an user given it's id. Null if user is not found
    */
  def find(id: Long): User

  /**
    * Returns all the users in the repository
    */
  def findAll(): Seq[User]

  /**
    * Stores a new user into the repository. It performs some validations before inserting,
    * if a validation doesn't pass, it throws an exception
    */
  def save(user: User): Unit

}

case class UserAlreadyExistsException(id: Long) extends RuntimeException(s"User [$id] already exists")

case class UserTooYoungException(age: Int) extends RuntimeException(s"The user is too young [$age], the minimum age is [${UserConstraints.MinAge}]")

case class NameTooLongException(name: String) extends RuntimeException(s"The user's name is too long [$name]. Max allowed length is [${UserConstraints.MaxNameLength}}")

case class EmptyNameException() extends RuntimeException(s"The user's first name is empty")
