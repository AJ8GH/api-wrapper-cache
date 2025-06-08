import org.w3c.dom.Node
import javax.xml.parsers.DocumentBuilderFactory

val formatString = "%.1f"
val defaultPercentage = 0.0

tasks.register("printLineCoverage") {
  group = "verification"
  description = "Generates the coverage badge"
  dependsOn("koverXmlReport")
  doLast {
    println(formatString.format(getCoverage(getFirstDocumentNode())))
  }
}

fun getFirstDocumentNode(): Node? = DocumentBuilderFactory
  .newInstance()
  .newDocumentBuilder()
  .parse(file("$projectDir/build/reports/kover/report.xml"))
  .firstChild
  .firstChild

fun getCoverage(node: Node?): Double {
  var currentNode = node
  while (currentNode != null) {
    if (currentNode.nodeName == "counter" && isLine(currentNode)) {
      return calculateCoverage(currentNode)
    }
    currentNode = currentNode.nextSibling
  }
  return defaultPercentage
}

fun calculateCoverage(node: Node): Double {
  val missedAttr = node.attributes.getNamedItem("missed")
  val coveredAttr = node.attributes.getNamedItem("covered")
  val missed = missedAttr.textContent.toLong()
  val covered = coveredAttr.textContent.toLong()
  return (covered * 100.0) / (missed + covered)
}

fun isLine(node: Node) = node
  .attributes
  .getNamedItem("type")
  .textContent == "LINE"
