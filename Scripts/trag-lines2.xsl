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
   
    <xsl:template match="//tei:l">
        <xsl:element name="l">
            <xsl:attribute name="n"><xsl:number count="tei:l" level="any"/></xsl:attribute>
            <xsl:if test="@cite">
                <xsl:attribute name="cite"><xsl:value-of select="current()/@cite"/></xsl:attribute>
            </xsl:if>
            <xsl:attribute name="ana"><xsl:value-of select="current()/@ana"/></xsl:attribute>
            <xsl:apply-templates/>          
        </xsl:element>
    </xsl:template>
  
 
  
    
    <xsl:template match="@*|node()" priority="-1">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>
</xsl:stylesheet>
