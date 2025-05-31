package io.github.aj8gh.weather.config

import io.kotest.core.config.AbstractProjectConfig

object KotestProjectConfig : AbstractProjectConfig() {
  override val globalAssertSoftly = true
}
