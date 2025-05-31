package io.github.aj8gh.weather.service

import io.github.aj8gh.weather.service.http.WeatherApiClient
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.equals.shouldBeEqual
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

private const val CITY = "london"
private const val RESPONSE_1 = "{}"
private const val RESPONSE_2 = """{"temperature": 24.3}"""

class WeatherServiceSpec : FunSpec({

  val client = mockk<WeatherApiClient>()
  val subject = WeatherService(client)

  context("should get weather from api") {
    withData(RESPONSE_1, RESPONSE_2) { response ->
      every { client.getWeather(CITY) }.returns(response)
      val actual = subject.getWeather(CITY)
      actual.trim() shouldBeEqual response.trim()
      verify { client.getWeather(CITY) }
    }
  }
})
