package io.github.aj8gh.weather

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.util.PlaceholderResolutionException

private val logger = KotlinLogging.logger { }

@EnableCaching
@SpringBootApplication
class WeatherApp

fun main(args: Array<String>) {
  try {
    runApplication<WeatherApp>(*args)
  } catch (e: PlaceholderResolutionException) {
    logger.error {
      "\n\n\n\n\n===!!!=== placeholder: ${e.placeholder} values: ${e.values} message: ${e.message} stacktrace: ${e.stackTrace} ===!!!===\n\n\n\n\n"
    }
  }
}
