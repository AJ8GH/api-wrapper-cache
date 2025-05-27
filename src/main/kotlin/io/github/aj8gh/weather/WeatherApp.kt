package io.github.aj8gh.weather

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WeatherApp

fun main(args: Array<String>) {
  println("Hello There!")
  runApplication<WeatherApp>(*args)
}
