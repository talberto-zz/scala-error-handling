package com.github.talberto.scalaerrorhandling

import java.time.{LocalDate, Period}

import scala.collection.mutable

case class ContactInfo(
                        country: Country,
                        city: String,
                        street: String,
                        postalCode: String
                      )

object UserConstraints {
  val MinAge = 18

  val MaxAge = 140

  val MaxNameLength = 20
}

case class User(
                 id: Long,
                 firstName: String,
                 lastName: String,
                 birthDate: LocalDate,
                 contactInfo: ContactInfo
               )

/**
  * Typical JPA like Repository to store users into a persistent database
  */
trait UserJpaRepository {

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

class InMemoryUserJpaRepository(private val users: mutable.Map[Long, User]) extends UserJpaRepository {

  override def find(id: Long): User = {
    users.getOrElse(id, null)
  }

  override def findAll(): Seq[User] = {
    users.values.to[Seq]
  }

  override def save(user: User): Unit = {
    if (users.contains(user.id)) {
      throw UserAlreadyExistsException(user.id)
    }

    val age = Period.between(user.birthDate, LocalDate.now()).getYears

    if (age < UserConstraints.MinAge) {
      throw UserTooYoungException(age)
    } else if (age > UserConstraints.MaxAge) {
      throw UserTooOldException(age)
    } else if (user.firstName.length > UserConstraints.MaxNameLength) {
      throw FirstNameTooLongException(user.firstName)
    } else if (user.lastName.length > UserConstraints.MaxNameLength) {
      throw LastNameTooLongException(user.lastName)
    } else if (user.firstName.trim.isEmpty) {
      throw EmptyFirstNameException()
    } else if (user.lastName.trim.isEmpty) {
      throw EmptyLastNameException()
    }

    users(user.id) = user
  }
}

object InMemoryUserJpaRepository extends InMemoryUserJpaRepository(mutable.Map.empty[Long, User])

case class UserAlreadyExistsException(id: Long) extends RuntimeException(s"User [$id] already exists")

case class UserTooYoungException(age: Int) extends RuntimeException(s"The user is too young [$age], the minimum age is [${UserConstraints.MinAge}]")

case class UserTooOldException(age: Int) extends RuntimeException(s"The user is too old [$age], the maximum age is [${UserConstraints.MaxAge}]")

case class FirstNameTooLongException(firstName: String) extends RuntimeException(s"The user's first name is too long [$firstName]. Max allowed length is [${UserConstraints.MaxNameLength}}")

case class LastNameTooLongException(lastName: String) extends RuntimeException(s"The user's last name is too long [$lastName]. Max allowed length is [${UserConstraints.MaxNameLength}}")

case class EmptyFirstNameException() extends RuntimeException(s"The user's first name is empty")

case class EmptyLastNameException() extends RuntimeException(s"The user's last name is empty")
