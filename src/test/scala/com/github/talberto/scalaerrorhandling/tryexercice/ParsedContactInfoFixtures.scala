package com.github.talberto.scalaerrorhandling.tryexercice

import com.github.talberto.scalaerrorhandling.tryexercice.exo.ParsedContactInfo

object ParsedContactInfoFixtures {
  val validContactInfo = ParsedContactInfo(
    countryIsoCode = "US",
    city = "NY"
  )
}
