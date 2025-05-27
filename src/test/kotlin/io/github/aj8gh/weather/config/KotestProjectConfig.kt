package io.github.aj8gh.weather.config

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.extensions.spring.SpringExtension

object KotestProjectConfig : AbstractProjectConfig() {
  override val globalAssertSoftly = true
  override fun extensions() = listOf(SpringExtension)
}
