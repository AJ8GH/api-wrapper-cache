package io.github.aj8gh.weather.api.controller

import io.github.aj8gh.weather.service.WeatherService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

const val WEATHER_PATH = "/v1/weather/{city}"

private val logger = KotlinLogging.logger {}

@RestController
class WeatherController(private val service: WeatherService) {

  @Cacheable("reports")
  @GetMapping(WEATHER_PATH)
  fun getWeather(@PathVariable city: String) = logger
    .info { "Retrieving weather for $city" }
    .let { service.getWeather(city) }
}
