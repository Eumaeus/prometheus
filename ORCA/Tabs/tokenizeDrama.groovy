import edu.harvard.chs.f1k.GreekNode
import edu.harvard.chs.cite.CiteUrn
import edu.harvard.chs.cite.CtsUrn
import groovy.io.FileType
import java.util.regex.Matcher
import java.util.regex.Pattern

// Tokenization Function
//			adds a space before punctuation
//			trims spaces on either end
//			tokenizes on whitespace
ArrayList tokenizeString(String str) {
  def puncTokenized = str.replaceAll(/([.,;·])/){ fullMatch, punc -> " ${punc}"  }
  ArrayList tokes = puncTokenized.trim().split(/[\s]+/)
  return tokes
}    


// Function to gather text nodes
ArrayList collectText(Object node){
    ArrayList returnVal = []
    
    // This is the CDATA of the XML source:
    if (node instanceof java.lang.String) {
        tokenizeString(node).each { t ->
			returnVal.add(t)
		}

    } else {

      // Not a leaf node: more markup to process.
      // This check grabs the local name whether or
      // not your XML is namespaced.
      String nodeName
      if (node.name() instanceof java.lang.String) {
			nodeName = node.name()
      } else {
			nodeName = node.name().getLocalPart()
      }


	  // We explicitely name each TEI element we want to preserve      
      switch (nodeName) {
      case "said":
		  //Collect all text data contained beneath a node:
		  GreekNode n = new GreekNode(node)
		  returnVal.add( tokenizeString(n.collectText()))
      break
      // keep walking:
      default:
      node.children().each { child ->
			  collectText(child).each { tokenList ->
			  returnVal.add(tokenList)
			  }
		  }
      break
      }
      return returnVal
    }
}


// Main Program

OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream('syntaxTokens.tsv'), "UTF-8");
OutputStreamWriter el = new OutputStreamWriter(new FileOutputStream('errors.log'), "UTF-8");

osw.write("seq\tciteUrn\tctsUrn\tanalysis\tsurfaceForm\n");

//Set up some variable
String urnPrefix = "urn:cite:fu:synTok_"
def enumerator = 0

//Set up some Patterns

	// match all text files 
def suffix = ~/.+\.txt$/; 
	// THERE ARE NO GENERIC ANALYSES! Here, we want to be sure we grab only leaf nodes, that is, tei:l elements; we will add tei:lg in a bit!
def pnot = ~/<l xmlns="http:\/\/www.tei-c.org\/ns\/1.0" +n=['"]([^"']+)['"]>/;

//Get Ready to parse tab-files in directory!

def tabDir = new File('.')
def tabFiles = []
tabDir.eachFile FileType.FILES,{
	assert suffix instanceof Pattern
	if (suffix.matcher(it.name) ){
		tabFiles << it.name
	}
}

// prepare for proper indexing of strings within citation leaf-node
def lastUrn = ""
def freqMap = [:]

// textIdString will hold a concatenationof textGroup+work+version, to be used in CITE Ids to ensure uniqueness
textIdString = ""
orcaUrn = ""

subref = ""


tabFiles.each { tf ->
	File f = new File(tf)
	f.eachLine { l ->
			
		if ( l =~ pnot ){
			def lrecords = l.split("#");
			if (lrecords.size() > 3){
				xs = lrecords[5]
				urn = lrecords[0]

				//for doing indexing of substrings
				if (urn != lastUrn){
					lastUrn = urn
					freqMap = [:]
				}

				//get compiled text nodes
				def root = new XmlParser().parseText(xs)
				def justText = collectText(root)
				justText.each{ t ->
				    t.each{
						if (it =~ "\\s"){ 
							el.write("${subref}\n")
						} else {

							workingCtsUrn = new CtsUrn(urn)

							// Increment enumerator
							enumerator++

							// Assemble ORCA CITE URN
							textIdString = "${workingCtsUrn.getTextGroup()}_${workingCtsUrn.getWork()}_${workingCtsUrn.getVersion()}"
							orcaUrn = "${urnPrefix}${textIdString}.${enumerator}"
						    
							// Make CTS+subref URN
								
						if (freqMap[it] == null){ //not yet in this leaf-node
							freqMap[it] = 1
							subref = "${it}[1]"
						} else { // this has already occurred in this leaf-node
							freqMap[it]++
							subref = "${it}[${freqMap[it]}]"
						}

							println it

							osw.write("${enumerator}\t${orcaUrn}\t${urn}@${subref}\t${orcaUrn}\t${it}\n")
						}
					} 
				}
				
			} else {
				el.write("${l}\n")
			}
		}
	}
}

osw.close()
el.close()
