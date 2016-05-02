package eitherexercice.dao

import java.time.{LocalDate, Period}

import eitherexercice.models._

import scala.collection.mutable

case class InMemoryUserRepository(private val users: mutable.Map[Long, User]) extends UserRepository {

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
    } else if (user.name.length > UserConstraints.MaxNameLength) {
      throw NameTooLongException(user.name)
    } else if (user.name.trim.isEmpty) {
      throw EmptyNameException()
    }

    users(user.id) = user
  }
}

object DefaultUserRepository extends InMemoryUserRepository(mutable.Map.empty[Long, User])
