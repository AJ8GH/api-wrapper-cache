package io.github.aj8gh.weather

import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.ok
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.client.WireMock.verify
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import io.github.aj8gh.weather.service.http.CITY_PLACEHOLDER
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.http.HttpStatus.OK
import org.springframework.test.context.ActiveProfiles

private const val CITY = "london"

@ActiveProfiles("test")
@WireMockTest(httpPort = 8888)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class WeatherSpringBootTest(
  @Autowired private val client: OkHttpClient,
  @Value("\${local.server.port}") private val port: Int,
  @Value("\${weather.api.path}") private val path: String,
  @Value("\${weather.api.query-string}") private val query: String,
) {

  @Test
  fun test() {
    val fullPath = "${path.replace(CITY_PLACEHOLDER, CITY)}$query"
    val expected = this::class.java
      .getResource("/response.json")!!
      .readText()

    stubFor(
      get(urlEqualTo(fullPath))
        .willReturn(ok().withBody(expected))
    )

    client.newCall(request())
      .execute()
      .let {
        it.code shouldBe OK.value()
        it.body.string() shouldBeEqual expected
        verify(getRequestedFor(urlEqualTo(fullPath)))
      }
  }

  private fun request() = Request.Builder()
    .url("http://localhost:$port/v1/weather/$CITY")
    .build()
}
