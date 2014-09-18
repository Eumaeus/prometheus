/* Align Texts
*
*  1. Input: 
*
*		a. a tab-delimited file, consisting of:
*
*			urn:of:unaligned:group.work.ed:node.citation	\t	urn:of:base:group.work.ed:node.cit-node.cite
*
*		b. CTS-tab-files of the unaligned-translation and the base-edition
*
*		c. path-to-textInventory.xml cataloging both.
*
*	2. Output:
*
*		a. a CTS-tabulation file
*
*		b. a CTS-RDF .ttl file
*
*/


import java.util.regex.Matcher
import java.util.regex.Pattern

import edu.harvard.chs.cite.CtsUrn
import edu.harvard.chs.cite.TextInventory

// Some useful regexes

def urnPattern = ~/(urn:cts:[^:]+:[^:]+:)([^#]+)/
def rangePattern = ~/(urn:cts:[^:]+:[^:]+:)([^-]+)-(.+)$/
def tossLinePattern = ~/^namespace/

// Inputs

def alignGuide = new File('new-alignment-guide.txt')
def ag = []

def baseTabs = new File('Iliad-Allen.txt')
def baseCites = [] // will hold all valid citations to the base text
def unalignedTabs = new File('Iliad-Butler.txt')
def unTabs = []

// Capture all tabs as list
unalignedTabs.eachLine { l ->
    if ( !(l =~ tossLinePattern) ) {
			unTabs << l
	}
}

def f = new File('/cite/dls/folio-cite/texts/editions/TextInventory.xml')
TextInventory ti = new TextInventory(f)

def fullAlignment = [] // this will hold the expanded alingment citations

// Capture alignGuide as a list
alignGuide.eachLine { l ->
           ag << l
}

// Capture citations from base text as a list
baseTabs.eachLine { l ->
    if ( !(l =~ tossLinePattern) ) {
			alignedUrn = l.split("#")[0]
			baseCites << alignedUrn
	}
}

// Create full alignment

def baseSeq = 0
CtsUrn baseUrn  // the urn of the base text. This will be a range urn.
def rangeBegin = "" // components of the base-text URN
def rangeEnd = ""
CtsUrn uaUrn  // the urn of the unaligned text. This will  be a node urn.
def uaCite = "" // the citation-component of the unaligned URN.
def tempCit = ""
def counter = 0
boolean isBaseUrnRange 

for (l in ag){  // cycle through the alignment guide
	baseUrn = new CtsUrn(l.tokenize("\t")[1])
	uaUrn = new CtsUrn(l.tokenize("\t")[0])
	
	isBaseUrnRange = baseUrn.isRange()
	println "${baseUrn.asString} is range = ${isBaseUrnRange}"
	println "inverse: ${!(isBaseUrnRange)}"

	if (isBaseUrnRange){
		rangeBegin = baseUrn.rangeBegin
		rangeEnd = baseUrn.rangeEnd
	} else {
		rangeBegin = baseUrn.passageNode
		rangeEnd = baseUrn.passageNode
	}

	uaCite = uaUrn.passageNode.toString().trim()
	
	println "'${uaCite}' '${rangeBegin}' ${uaCite == rangeBegin}"

	if ( uaCite == rangeBegin ){
		fullAlignment << "${uaUrn.asString}\t${baseUrn.getUrnWithoutPassage()}:${rangeBegin}"
		for (i in (0..1000)){
			baseSeq++ // increment!
			if (baseSeq > (baseCites.size() -1)){ break }
			baseUrn = new CtsUrn(baseCites[baseSeq])
			tempCite = baseUrn.passageNode
			println "'${tempCite}' '${rangeEnd}' ${tempCite == rangeEnd} "
			fullAlignment << "${uaUrn.asString}\t${baseUrn.getUrnWithoutPassage()}:${tempCite}"
			if (!(isBaseUrnRange)){ 
				baseSeq++
			    break 
			}
			if (tempCite == rangeEnd) {
				baseSeq++
				break
			}
		}
	} 
}

println "Got here"

// At this point… we have fullAlignment, which contains a urn-to-unaligned text and a urn-to-each-node-of-aligned text

// …but we need to get next- and previous- info, which requires two loops, one backward and one forward
//		We'll add those onto the end of our fullAlignment list: seq \t prev \t next


def agEnd = (ag.size() - 1) // So we can loop down through the alignment-guide (ag[]) ; ag[] has one line for each citation in the unaligned text
def faEnd = (fullAlignment.size() - 1) // So we can loop through the fullAlignment[]
def upCounter = 0
def downCounter = 0
def thisNext = " "
def thisPrev = " "
def thisSeq = ""

// We'll keep using baseUrn and uaUrn


for (i in (0..faEnd)){
    baseUrn = new CtsUrn(fullAlignment[i].split("\t")[1])
    uaUrn = new CtsUrn(fullAlignment[i].split("\t")[0])

    baseCite = baseUrn.passageNode
 	uaCite = uaUrn.passageNode

    if ( baseCite == uaCite){
        thisPrev = i.toString()
    }
    if (thisPrev == "0"){thisPrev = ""}

    fullAlignment[i] +=  "\t${(i+1).toString()}"
    fullAlignment[i] +=  "\t${thisPrev}"

}

for (i in (faEnd..0)){
    baseUrn = new CtsUrn(fullAlignment[i].split("\t")[1])
    uaUrn = new CtsUrn(fullAlignment[i].split("\t")[0])


    baseCite = baseUrn.passageNode
 	uaCite = uaUrn.passageNode

    fullAlignment[i] +=  "\t${thisNext}"
    if ( baseCite == uaCite ){
        thisNext = fullAlignment[i].split("\t")[2]
    }
}


// Now we can set up to write some tab-files!


// Get some variables for the output file. Start by grabbing a URN from the fullAlignment list


CtsUrn oSampleUrn = new CtsUrn(fullAlignment[0].split("\t")[0])
def oNewVersion = oSampleUrn.getUrnWithoutPassage()
def oWork = "urn:cts:${oSampleUrn.ctsNamespace}:${oSampleUrn.textGroup}.${oSampleUrn.work}"
def outputTabsFilename = "${oSampleUrn.textGroup}-${oSampleUrn.work}-${oSampleUrn.version}-alignedTabs.txt"
def outputRdfFilename = "${oSampleUrn.textGroup}-${oSampleUrn.work}-${oSampleUrn.version}-RDF.ttl"
OutputStreamWriter ors = new OutputStreamWriter(new FileOutputStream(outputRdfFilename), "UTF-8")

// variables useful before we loop
def oNsAbbrev = ""
def oNsUri = ""
def translationLang = ""

ti.xmlNsForVersion(oNewVersion).each { i ->
	oNsAbbrev = "${i.key}"
	oNsUri =  "${i.value}"
}

translationLang = ti.languageForVersion(oNewVersion)

// Write prelims
//		rdf-file
ors.write("<${oNewVersion}> cts:xmlns <${oNsUri}> .\n")
ors.write("<${oNewVersion}> cts:belongsTo <${oWork}> .\n")
ors.write("<${oWork}> cts:possesses <${oNewVersion}> .\n")
ors.write("""<${oNewVersion}> dcterms:title "${ti.workTitle(oWork)}" .\n""")
ors.write("<${oNewVersion}> rdf:type cts:Translation .\n")
ors.write("""<${oNewVersion}> rdf:label "${ti.versionLabel(oNewVersion)}" .\n""")
ors.write("""<${oNewVersion}> cts:translationLang "${translationLang}" .\n""")
ors.write("\n")

// Looping!
//      remember fullAlignment[n] --> uaURN \t baseUrn \t seq \t prev \t next

def tabCounter = -1 
def citationDepth = oSampleUrn.getCitationDepth()
def cDA = [citationDepth] // this handles different levels of citation
def nDA = [citationDepth] // the same thing, but "new", for comparison
def oSeq, oNext, oPrev
def iOpen, iText, iScope
uaCite = ""

if (citationDepth > 1){
        for (x in (0..(citationDepth - 1))){
            cDA[x] = oSampleUrn.passageNode.tokenize('.')[x]
            nDA[x] = "-"
        }
}

oSeq = 0
oNext = ""
oPrev = ""

fullAlignment.each { l ->

        baseUrn = new CtsUrn(l.split("\t")[1])
        baseCite = baseUrn.passageNode
        uaUrn = new CtsUrn(l.split("\t")[0]) 
        uaCite = uaUrn.passageNode
        if (baseCite == uaCite){
                if (tabCounter < (unTabs.size() - 1)){
                    tabCounter++
                }
        }


        iOpen = unTabs[tabCounter].split("#")[4]
        iText = unTabs[tabCounter].split("#")[5]
        iScope = unTabs[tabCounter].split("#")[6]

        oUrn = "${uaUrn.getUrnWithoutPassage()}:${baseUrn.passageNode}"
		
        //RDF!
        // Sequence is easy

		oSeq++

		//for next and prev, we need to use the unTabs for OHCO navigation
        if (tabCounter == 0){ 
			oPrev = "" 
		} else { 
			oPrev = unTabs[tabCounter-1].split("#")[0]
            ors.write("<${oUrn}> cts:prev <${oNewVersion}:${new CtsUrn(oPrev).passageNode}> .\n") 
		}
        if (tabCounter < (unTabs.size()-2)){ 
			oNext = unTabs[tabCounter + 1].split("#")[0]
            ors.write("<${oUrn}> cts:next <${oNewVersion}:${new CtsUrn(oNext).passageNode}> .\n") 
		} else {
			oNext = "" 
		}

		ors.write("\n")

        ors.write("<${oUrn}> cts:belongsTo <${oNewVersion}> .\n")
        ors.write("""<${oUrn}> rdf:label "${ti.versionLabel(oNewVersion)}: ${baseCite}" .\n""")
        ors.write("<${oUrn}> cts:hasSequence ${oSeq} .\n")
        ors.write("<${oUrn}> cts:hasTextContent " + '"""' + "${iText}" + '"""' + " .\n")
        ors.write("<${oUrn}> cts:citationDepth ${citationDepth} .\n")
        ors.write("<${oUrn}> hmt:xmlOpen " + '"' + iOpen + '"' + " .\n")
        ors.write("<${oUrn}> hmt:xpTemplate " + '"' + iScope + '"' + " .\n")


        // We need to do the citation-hieararchy-depth thing
        // Only loop down to the next-to-last
        if (citationDepth > 1){
                // e.g. cd=2 0..0, cd=3  0..1
                for (x in (0..(citationDepth - 2))){
                   //println "x = ${x}" 
                    // if the new one doesn't match the old one, write out the 'belongs-to'
                    if (nDA[x] != cDA[x]){
                        nDA[x] = baseUrn.passageNode.tokenize('.')[x]
                        cDA[x] = nDA[x]
                        ors.write("<${uaUrn.getUrnWithoutPassage()}:${cDA[x]}> cts:citationDepth ${x+1} .\n")
                    } else {
                        nDA[x] = baseUrn.passageNode.tokenize('.')[x]
                    }
                    tempCiteBig = "${baseUrn.passageNode.tokenize('.')[x]}.${baseUrn.passageNode.tokenize('.')[x+1]}"
                    tempCiteSmaller = "${nDA[x]}"
                    
                    ors.write("<${uaUrn.getUrnWithoutPassage()}:${tempCiteBig}> cts:containedBy <${uaUrn.getUrnWithoutPassage()}:${tempCiteSmaller}> .\n")
                    ors.write("<${uaUrn.getUrnWithoutPassage()}:${tempCiteSmaller}> cts:contains <${uaUrn.getUrnWithoutPassage()}:${tempCiteBig}> .\n")
                    
                }
        }
        
        ors.write("\n")

}


ors.close()

// Scratch




