<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:tei="http://www.tei-c.org/ns/1.0" xmlns="http://www.tei-c.org/ns/1.0"
    exclude-result-prefixes="#all">
    <xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>



    <xsl:template match="tei:l">
        <xsl:element name="l">
            <xsl:choose>
                <xsl:when test="@n">
                    <xsl:attribute name="n">
                        <xsl:value-of select="@n"/>
                    </xsl:attribute>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:attribute name="n">
                        <xsl:number level="single"/>
                    </xsl:attribute>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:apply-templates/>
        </xsl:element>
    </xsl:template>



    <xsl:template match="@*|node()" priority="-1">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>
</xsl:stylesheet>
