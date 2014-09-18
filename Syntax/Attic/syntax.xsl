<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    version="2.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:tei="http://www.tei-c.org/ns/1.0" 
    xmlns="http://www.tei-c.org/ns/1.0"
    exclude-result-prefixes="#all"
    >
    <xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>
   
   <xsl:template match="sentence">
       <xsl:apply-templates/>
   </xsl:template>
    
    <xsl:template match="word">
        <xsl:element name="word">
            <xsl:attribute name="sentenceId"><xsl:value-of select="./ancestor::sentence/@id"/></xsl:attribute>
            <xsl:attribute name="wordId"><xsl:value-of select="@cid"/></xsl:attribute>
            <xsl:attribute name="postag"><xsl:value-of select="@postag"/></xsl:attribute>
            <xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
            <xsl:attribute name="head"><xsl:value-of select="@head"/></xsl:attribute>
            <xsl:attribute name="relation"><xsl:value-of select="@relation"/></xsl:attribute>
            <xsl:element name="token">
                <xsl:attribute name="lang">grc</xsl:attribute>
                <xsl:value-of select="@form"/>
            </xsl:element>
            <xsl:element name="lemma">
                <xsl:attribute name="lang">grc</xsl:attribute>
                <xsl:value-of select="@lemma"/>
            </xsl:element>
        </xsl:element>
    </xsl:template>
   
  
    
    <xsl:template match="@*|node()" priority="-1">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>
</xsl:stylesheet>
