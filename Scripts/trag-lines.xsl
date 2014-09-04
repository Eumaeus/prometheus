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
        <xsl:choose>
            <xsl:when test="@n">
                <xsl:choose>
                <xsl:when test="contains(@n,'b')">
                <xsl:call-template name="funkyNumber"></xsl:call-template>
                </xsl:when>
                    <xsl:when test="contains(@n,'a')">
                        <xsl:call-template name="funkyNumber"></xsl:call-template>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:call-template name="saveNumber"></xsl:call-template>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="incNumber"></xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
  
  <xsl:template name="incNumber">
      <xsl:element name="l">
         
          <xsl:attribute name="ana"><xsl:value-of select="current()/@ana"/></xsl:attribute>
          <xsl:apply-templates/>          
      </xsl:element>
  </xsl:template>
    <xsl:template name="saveNumber">
        <xsl:element name="l">
            <xsl:attribute name="cite"><xsl:value-of select="current()/@n"/></xsl:attribute>
            <xsl:attribute name="ana"><xsl:value-of select="current()/@ana"/></xsl:attribute>
            <xsl:apply-templates/>          
        </xsl:element>
    </xsl:template>
    
    <xsl:template name="funkyNumber">
        <xsl:element name="seg">
            <xsl:attribute name="n"><xsl:value-of select="current()/@n"/></xsl:attribute>
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
