package io.github.aj8gh.weather.service.http

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.equals.shouldBeEqual
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody

private const val CONTENT_1 = "{}"
private const val CONTENT_2 = """{"temperature": 24.3}"""
private const val CITY = "london"

class WeatherApiClientSpec : FunSpec({

  val url = "http://localhost:8080"
  val path = "/weather/$CITY_PLACEHOLDER"
  val query = "?unit=metric"
  val client = mockk<OkHttpClient>()
  val subject = WeatherApiClient(client = client, url = url, path = path, query = query)

  context("should call API") {
    withData(CONTENT_1, CONTENT_2) { content ->
      val call = mockk<Call>()
      val response = mockk<Response>()
      val responseBody = mockk<ResponseBody>()
      val expectedUrl = "$url${path.replace(CITY_PLACEHOLDER, CITY)}$query"

      every { client.newCall(match { it.url.toString() == expectedUrl }) }.returns(call)
      every { call.execute() }.returns(response)
      every { response.body }.returns(responseBody)
      every { responseBody.string() }.returns(content)

      val actual = subject.getWeather(CITY)

      actual shouldBeEqual content
      verify { client.newCall(match { it.url.toString() == expectedUrl }) }
    }
  }
})
