package eitherexercice

import java.time.LocalDate

import eitherexercice.exo.ParsedUser

object ParsedUserFixtures {
  val validParsedUser = ParsedUser(
    name = "John Doe",
    birthDate = LocalDate.parse("1975-07-07"),
    contactInfo = ParsedContactInfoFixtures.validContactInfo
  )
}
