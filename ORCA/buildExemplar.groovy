@GrabResolver(name='custom', root='http://beta.hpcc.uh.edu/nexus/content/repositories/releases')
@Grab(group='org.homermultitext', module='citemgr', version='0.5.17')

import org.homermultitext.citemanager.AnalyticalExemplarBuilder


if (args.size() != 6) {
  println "Usage: groovy buildExemplar.groovy <TABFILE> <Analytical Record> <Exemplar ID String> <Text Analyzed (CTS w/subref)> <Analysis> <Text-Content of Examplar Node>"
  println args
} else {
  String analysisRec = args[1]
  String newCollection = args[2]
  String ctsColumn = args[3]
  String citeColumn = args[4]
  String txtChunk = args[5]
  File testFile = new File(args[0])
  AnalyticalExemplarBuilder aeb = new AnalyticalExemplarBuilder()
  aeb.debug = 1
  println aeb.rdfFromTsv(testFile, analysisRec, citeColumn, ctsColumn, txtChunk, newCollection, true)
}
