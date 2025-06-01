package io.github.aj8gh.weather.config

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.boot.test.context.TestConfiguration
import redis.embedded.RedisServer

@TestConfiguration
class TestRedisConfiguration(properties: RedisProperties) {

  private val server: RedisServer = RedisServer(properties.port)

  @PostConstruct
  fun postConstruct() {
    server.start()
  }

  @PreDestroy
  fun preDestroy() {
    server.stop()
  }
}
