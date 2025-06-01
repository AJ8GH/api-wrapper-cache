package io.github.aj8gh.weather.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import java.time.Duration

@Configuration
@EnableRedisRepositories
class RedisConfig(
  @Value("\${weather.cache.ttl-seconds}")
  private val ttlSeconds: Long,
) {

  @Bean
  fun redisCacheManager(
    factory: RedisConnectionFactory,
    config: RedisCacheConfiguration,
  ): RedisCacheManager = RedisCacheManager
    .builder(factory)
    .cacheDefaults(config)
    .build()

  @Bean
  fun redisCacheConfiguration(): RedisCacheConfiguration =
    RedisCacheConfiguration
      .defaultCacheConfig()
      .entryTtl(Duration.ofSeconds(ttlSeconds))
}
