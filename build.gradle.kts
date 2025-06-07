import javax.xml.parsers.DocumentBuilderFactory

plugins {
  alias(libs.plugins.java.library)
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.kotlin.spring)
  alias(libs.plugins.kover)
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
        classes("$group.weather.WeatherApp", "$group.weather.WeatherAppKt")
      }
    }
  }
}

tasks.register("printLineCoverage") {
  group = "verification" // Put into the same group as the `kover` tasks
  description = "Generates the coverage badge"
  dependsOn("koverXmlReport")
  doLast {
    val report = file("$projectDir/build/reports/kover/report.xml")
    val doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(report)
    var childNode = doc.firstChild.firstChild
    var coveragePercent = 0.0
    while (childNode != null) {
      if (childNode.nodeName == "counter") {
        val typeAttr = childNode.attributes.getNamedItem("type")
        if (typeAttr.textContent == "LINE") {
          val missedAttr = childNode.attributes.getNamedItem("missed")
          val coveredAttr = childNode.attributes.getNamedItem("covered")
          val missed = missedAttr.textContent.toLong()
          val covered = coveredAttr.textContent.toLong()
          coveragePercent = (covered * 100.0) / (missed + covered)
          break
        }
      }
      childNode = childNode.nextSibling
    }
    println("%.1f".format(coveragePercent))
  }
}
