/*
*/

// Remember to put the transcoder jar on your classpath!
import edu.unc.epidoc.transcoder.*


def converter = new BetaConverter()

// Wrap system.out in utf8-ified outputstreamwriter, and include this in
// call to checkNode:
OutputStreamWriter outputStreamWriter = new OutputStreamWriter(System.out, "UTF-8")
checkNode(root, converter, outputStreamWriter, "")
outputStreamWriter.close();
// END OF SCRIPT



/**
* Creates String of name='value' patterns from the
* attributes of an Element.
* param e The Element with attributes.
* returns A String to include within the opening tag of an element.
*/
String collectAttrs(Element e) {
    NamedNodeMap attrs = e.getAttributes();
    String concatenated = ""
    // Get number of attributes in the element
    int numAttrs = attrs.getLength();
    
    // Process each attribute
      int i = 0
      while (i < numAttrs) {
        Attr attr = (Attr)attrs.item(i);
	concatenated = concatenated + " " +  attr.getNodeName() + "='" +attr.getNodeValue() + "'";
	i++
    }
    return(concatenated)
}



/**
*/
def checkNode(Node n, BetaConverter conv, OutputStreamWriter osw, String lang) {
        boolean closeMe = false

       switch (n.nodeType) {
       case Node.ELEMENT_NODE:
       	    osw.write( "<" + n.nodeName + " " + collectAttrs(n) )
	    if  (n.hasAttribute("lang")) {
	    //	    conv.setLang(n.getAttribute("lang"))
	    lang = n.getAttribute("lang")
	    }
	    if (n.childNodes.getLength() > 0) {
	    osw.write(">")
	    closeMe = true
	    } else {
	    osw.write("/>")
	    }
	    break

       case Node.TEXT_NODE:
//           if (conv.currentLang == "grc") {
           if (lang == "grc") {
	   osw.write(conv.tc.getString(n.nodeValue))
	   } else {
	   if (n.nodeValue) {       	    osw.write( n.nodeValue) }
	   }
	   break

	otherwise
		// do nothing
       }

     n.getChildNodes().each { kid -> checkNode(kid,conv,osw,lang)  }
     if (closeMe) { osw.write("</" + n.nodeName + ">") }
}


/**
*	Object for converting beta code Greek to Unicode-C.
*/
class BetaConverter {
      /**
      * The transcoder object
      */
      TransCoder tc 
      /**
      * Value of currently applicable @lang attribute.
      */
      String currentLang = "none"

      /** Constructor initializes transcoder object.
      */
      BetaConverter() {
       tc = new TransCoder()
       tc.setParser("BetaCode");
       tc.setConverter("UnicodeC");
      }

      /** Transcodes a string or not, depending on
      * value of the currentLang setting.
      * param s The String to convert.
      * returns A transcoded String if our lang setting is "grc"
      */
      String convert(String s) {
      if (currentLang == "grc") {
      	 return(tc.getString(s))
	} else {
	return(s)
	}
      }

      /** Sets value of currentLang.
      * param lang New value to use for currentLang.
      */
      void setLang(String lang) {
      currentLang = lang
      }
}// BetaConverter


