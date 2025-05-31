package io.github.aj8gh.weather.service

import io.github.aj8gh.weather.service.http.WeatherApiClient
import org.springframework.stereotype.Service

@Service
class WeatherService(private val client: WeatherApiClient) {

  fun getWeather(city: String) = client.getWeather(city)
}
