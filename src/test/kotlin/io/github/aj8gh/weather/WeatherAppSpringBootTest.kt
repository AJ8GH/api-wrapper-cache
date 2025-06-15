package io.github.aj8gh.weather

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.ok
import com.github.tomakehurst.wiremock.client.WireMock.resetAllRequests
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.client.WireMock.verify
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import io.github.aj8gh.weather.config.TestRedisConfiguration
import io.github.aj8gh.weather.service.http.CITY_PLACEHOLDER
import io.kotest.matchers.maps.shouldContain
import io.kotest.matchers.shouldBe
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.cache.CacheManager
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus.OK
import org.springframework.test.context.ActiveProfiles

private const val CITY = "london"

@ActiveProfiles("test")
@WireMockTest(httpPort = 8888)
@Import(TestRedisConfiguration::class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class WeatherSpringBootTest(
  @Autowired private val client: OkHttpClient,
  @Autowired private val objectMapper: ObjectMapper,
  @Autowired private val cacheManager: CacheManager,
  @LocalServerPort private val port: Int,
  @Value("\${weather.api.path}") private val path: String,
  @Value("\${weather.api.query-string}") private val query: String,
) {

  @AfterEach
  fun tearDown() {
    WireMock.reset()
    cacheManager.cacheNames.forEach { cacheManager.getCache(it)?.clear() }
  }

  @Test
  fun shouldReturnReport() {
    val expected = response()
    stubResponse(expected)

    val response = makeGetRequest()
    assertResponse(response, expected)
    verifyExternalApiCalls(1)
  }

  @Test
  fun shouldCacheReport() {
    val expected = response()
    stubResponse(expected)

    val response = makeGetRequest()
    assertResponse(response, expected)
    verifyExternalApiCalls(1)

    resetAllRequests()

    val response1 = makeGetRequest()
    assertResponse(response1, expected)
    verifyExternalApiCalls(0)
  }

  private fun makeGetRequest() =
    client.newCall(request()).execute()

  private fun assertResponse(response: Response, expected: String) =
    response.let {
      it.code shouldBe OK.value()
      it.body.string()
        .let { body -> asMap(body) }
        .let { responseMap ->
          responseMap shouldContain Pair("payload", asMap(expected))
          responseMap shouldContain Pair("city", CITY)
        }
    }

  private fun verifyExternalApiCalls(times: Int) =
    verify(times, getRequestedFor(urlEqualTo(path())))

  private fun request() = Request.Builder()
    .url(url(port))
    .build()

  private fun asMap(value: String) =
    objectMapper.readValue(value, object : TypeReference<Map<String, Any>>() {})

  private fun path() = "${path.replace(CITY_PLACEHOLDER, CITY)}$query"

  private fun url(port: Int) = "http://localhost:$port/v1/weather/$CITY"

  private fun response() = this::class.java
    .getResource("/response.json")!!
    .readText()

  private fun stubResponse(response: String) = stubFor(
    get(urlEqualTo(path()))
      .willReturn(ok().withBody(response))
  )
}
