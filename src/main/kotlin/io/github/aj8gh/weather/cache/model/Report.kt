package io.github.aj8gh.weather.cache.model

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.io.Serializable
import java.time.Instant

@RedisHash
data class Report(
  @Id val city: String,
  val payload: Map<String, Any>,
  val createdAt: Instant = Instant.now(),
  val updatedAt: Instant = Instant.now(),
) : Serializable
