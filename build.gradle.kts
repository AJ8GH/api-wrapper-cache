plugins {
  alias(libs.plugins.java.library)
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.kotlin.spring)
  alias(libs.plugins.spring.boot)
  alias(libs.plugins.spring.dependency.management)
}

repositories {
  mavenLocal()
  mavenCentral()
}

group = properties["project.group.id"]!!
version = properties["project.version"]!!

kotlin {
  jvmToolchain(libs.versions.java.get().toInt())
}

tasks.named<Test>("test") {
  useJUnitPlatform()
}

dependencies {
  implementation(libs.bundles.implementation)
  runtimeOnly(libs.bundles.runtimeOnly)
  testImplementation(libs.bundles.test.implementation)
}
