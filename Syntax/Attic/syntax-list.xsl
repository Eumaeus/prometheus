<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    version="2.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:tei="http://www.tei-c.org/ns/1.0" 
    xmlns="http://www.tei-c.org/ns/1.0"
    exclude-result-prefixes="#all"
    >
    <xsl:template match="/">
        <xsl:for-each select="//word">
            <xsl:value-of select="current()/token"/><xsl:text> </xsl:text><xsl:value-of select="current()/@sentenceId"/>-<xsl:value-of select="@wordId"/><xsl:text>
                
            </xsl:text>
        </xsl:for-each> 
    </xsl:template>
    
    <!--<xsl:template match="annotator"/>
    <xsl:template match="token"/>
    <xsl:template match="lemma"/>-->
   
   
    <xsl:template match="@*|node()" priority="-1"/>
    
</xsl:stylesheet>
