package io.github.aj8gh.weather.service

import io.github.aj8gh.weather.service.http.WeatherApiClient
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger { }

@Service
class WeatherService(private val weatherClient: WeatherApiClient) {

  fun getWeather(city: String) = weatherClient
    .getWeather(city)
    .also { logger.info { "Retrieved weather report for $city from API" } }
}
