package io.github.aj8gh.weather

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@EnableCaching
@SpringBootApplication
class WeatherApp

fun main(args: Array<String>) {
  runApplication<WeatherApp>(*args)
}
