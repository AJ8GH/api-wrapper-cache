import javax.xml.parsers.DocumentBuilderFactory

tasks.register("printLineCoverage") {
  group = "verification"
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
