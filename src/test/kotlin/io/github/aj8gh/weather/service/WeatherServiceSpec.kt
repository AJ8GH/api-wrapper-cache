package io.github.aj8gh.weather.service

import io.github.aj8gh.weather.cache.model.Report
import io.github.aj8gh.weather.service.http.WeatherApiClient
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.equals.shouldBeEqual
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

private const val CITY = "london"
private val response1 = mapOf<String, Any>()
private val response2 = mapOf<String, Any>(Pair("temperature", 24.3))

class WeatherServiceSpec : FunSpec({

  val client = mockk<WeatherApiClient>()
  val subject = WeatherService(client)

  context("should get weather from api") {
    withData(response1, response2) { response ->
      val report = Report(city = CITY, payload = response)
      every { client.getWeather(CITY) }.returns(report)
      val actual = subject.getWeather(CITY)
      actual shouldBeEqual report
      verify { client.getWeather(CITY) }
    }
  }
})
