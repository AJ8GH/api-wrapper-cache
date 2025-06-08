plugins {
  alias(libs.plugins.java.library)
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.kotlin.spring)
  alias(libs.plugins.kover)
  alias(libs.plugins.kover.config)
  alias(libs.plugins.spring.boot)
  alias(libs.plugins.spring.dependency.management)
}

repositories {
  mavenLocal()
  mavenCentral()
  gradlePluginPortal()
}

group = properties["project.group.id"]!!
version = properties["project.version"]!!

kotlin {
  jvmToolchain(libs.versions.java.get().toInt())
}

tasks.withType(Test::class) {
  useJUnitPlatform()
  finalizedBy(
    tasks.named("koverHtmlReport"),
    tasks.named("koverXmlReport"),
  )
}

dependencies {
  implementation(libs.bundles.implementation)
  runtimeOnly(libs.bundles.runtimeOnly)
  testImplementation(libs.bundles.test.implementation)
}

kover {
  reports {
    filters {
      excludes {
        classes(
          "$group.weather.WeatherApp",
          "$group.weather.WeatherAppKt"
        )
      }
    }
  }
}
