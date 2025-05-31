//package io.github.aj8gh.weather
//
//import com.github.tomakehurst.wiremock.client.WireMock.get
//import com.github.tomakehurst.wiremock.client.WireMock.ok
//import com.github.tomakehurst.wiremock.client.WireMock.stubFor
//import com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo
//import io.github.aj8gh.weather.config.RestClientConfig
//import io.kotest.core.spec.style.FunSpec
//import io.kotest.extensions.spring.SpringExtension
//import io.kotest.matchers.equals.shouldBeEqual
//import io.kotest.matchers.shouldBe
//import org.springframework.http.HttpStatus.OK
//import org.springframework.test.context.ContextConfiguration
//import org.springframework.web.client.RestClient
//
//private const val WEATHER_API_PATH = "/VisualCrossingWebServices/rest/services/timeline/{city}"
//private const val HTTP_PORT = 80
//
////@WireMockTest(httpPort = HTTP_PORT)
//@ContextConfiguration(classes = [RestClientConfig::class])
//class WeatherAppSpec(
//  private val client: RestClient,
//) : FunSpec({
//
//  val port = 8080
//
//  test("returns 200") {
//    val expected = WeatherAppSpec::class.java
//      .getResource("/response.json")!!
//      .readText()
//
//    stubFor(
//      get(urlPathEqualTo(WEATHER_API_PATH))
//        .willReturn(ok().withBody(expected))
//    )
//
//    client.get()
//      .uri("http://localhost:$port/v1/weather/{city}", "london")
//      .retrieve()
//      .toEntity(String::class.java)
//      .let {
//        it.statusCode shouldBe OK
//        it.body shouldBeEqual expected
//      }
//
////    verify(getRequestedFor(urlEqualTo(WEATHER_API_PATH)))
//  }
//
//}) {
//
//  override fun extensions() = listOf(
//    SpringExtension,
//  )
//
//}
