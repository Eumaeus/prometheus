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
   
   <xsl:template match="tei:speaker"/>
    
    <xsl:template match="tei:sp"><xsl:apply-templates/></xsl:template>
    <xsl:template match="tei:div1"><xsl:apply-templates/></xsl:template>
    <xsl:template match="tei:div2"><xsl:apply-templates/></xsl:template>
  
  <xsl:template match="tei:div1[@type='episode']/tei:sp/tei:l">
      <xsl:element name="l">
          <xsl:if test="./@n">
          <xsl:attribute name="n"><xsl:value-of select="@n"/></xsl:attribute>
          </xsl:if>
          <xsl:attribute name="ana">episode</xsl:attribute>
          <xsl:element name="said">
              <xsl:attribute name="who"><xsl:value-of select="./preceding-sibling::tei:speaker"/></xsl:attribute>
          <xsl:apply-templates/>
          </xsl:element>
      </xsl:element>
  </xsl:template>
    
    <xsl:template match="tei:div1[@type='choral']/tei:div2/tei:sp/tei:l">
        <xsl:element name="l">
            <xsl:if test="./@n">
                <xsl:attribute name="n"><xsl:value-of select="@n"/></xsl:attribute>
            </xsl:if>
            <xsl:attribute name="ana"><xsl:value-of select="./ancestor::tei:div2/@type"/></xsl:attribute>
            <xsl:element name="said">
                <xsl:attribute name="who"><xsl:value-of select="./preceding-sibling::tei:speaker"/></xsl:attribute>
                <xsl:apply-templates/>
            </xsl:element>
        </xsl:element>
    </xsl:template>
    
    <xsl:template match="tei:div1[@type='episode']/tei:div2/tei:sp/tei:l">
        <xsl:element name="l">
            <xsl:if test="./@n">
                <xsl:attribute name="n"><xsl:value-of select="@n"/></xsl:attribute>
            </xsl:if>
            <xsl:attribute name="ana"><xsl:value-of select="./ancestor::tei:div2/@type"/></xsl:attribute>
            <xsl:element name="said">
                <xsl:attribute name="who"><xsl:value-of select="./preceding-sibling::tei:speaker"/></xsl:attribute>
                <xsl:apply-templates/>
            </xsl:element>
        </xsl:element>
    </xsl:template>
    
    <xsl:template match="@*|node()" priority="-1">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>
</xsl:stylesheet>
