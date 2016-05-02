package com.github.talberto.scalaerrorhandling.tryexercice

import java.time.LocalDate

import com.github.talberto.scalaerrorhandling.tryexercice.models.{ContactInfo, Country, User}

object UserFixtures {

  val validUser = {
    val contactInfo = ContactInfo(
      country = Country("FR", "France"),
      city = "Paris"
    )
    User(
      id = 1005L,
      name = "John Doe",
      birthDate = LocalDate.parse("1953-03-05"),
      contactInfo = contactInfo
    )
  }
}
