package io.github.aj8gh.weather.service.http

import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

const val CITY_PLACEHOLDER = "{city}"

@Component
class WeatherApiClient(
  private val client: OkHttpClient,
  @Value("\${weather.api.url}") private val url: String,
  @Value("\${weather.api.path}") private val path: String,
  @Value("\${weather.api.query-string}") private val query: String,
) {

  fun getWeather(city: String) = client.newCall(request(city))
    .execute()
    .body
    .string()

  private fun request(city: String) = Request.Builder()
    .url("$url${path.replace(CITY_PLACEHOLDER, city)}$query")
    .build()
}
