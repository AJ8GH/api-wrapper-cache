package io.github.aj8gh.weather.service.http

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
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

private val payload1 = mapOf<String, Any>()
private val payload2 = mapOf<String, Any>(Pair("temperature", 24.3))

class WeatherApiClientSpec : FunSpec({

  val url = "http://localhost:8080"
  val path = "/weather/$CITY_PLACEHOLDER"
  val query = "?unit=metric"
  val client = mockk<OkHttpClient>()
  val objectMapper = mockk<ObjectMapper>()
  val subject = WeatherApiClient(
    client = client,
    url = url,
    path = path,
    query = query,
    objectMapper = objectMapper,
  )

  context("should call API") {
    withData(
      Pair(CONTENT_1, payload1),
      Pair(CONTENT_2, payload2)
    ) { pair ->
      val call = mockk<Call>()
      val response = mockk<Response>()
      val responseBody = mockk<ResponseBody>()
      val expectedUrl = "$url${path.replace(CITY_PLACEHOLDER, CITY)}$query"

      every { client.newCall(match { it.url.toString() == expectedUrl }) }.returns(call)
      every { call.execute() }.returns(response)
      every { response.body }.returns(responseBody)
      every { responseBody.string() }.returns(pair.first)
      every { objectMapper.readValue(pair.first, any(TypeReference::class)) }
        .returns(pair.second)

      val actual = subject.getWeather(CITY)

      actual.payload shouldBeEqual pair.first
      actual.city shouldBeEqual CITY
      verify { client.newCall(match { it.url.toString() == expectedUrl }) }
    }
  }
})
