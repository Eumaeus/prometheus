<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:tei="http://www.tei-c.org/ns/1.0" xmlns="http://www.tei-c.org/ns/1.0"
    exclude-result-prefixes="tei">

    <xsl:variable name="unalignedUrn">urn:cts:greekLit:tlg0012.tlg001.persEng:</xsl:variable>
    <xsl:variable name="baseUrn">urn:cts:greekLit:tlg0012.tlg001.fuPers:</xsl:variable>

    <xsl:template match="/">
        <xsl:apply-templates select="//tei:p"/>
    </xsl:template>


    <xsl:template match="tei:p">
        <xsl:value-of select="$unalignedUrn"/><xsl:value-of select="ancestor::tei:div1/@n"
            />.<xsl:value-of select="./@n"/><xsl:text>\t</xsl:text><xsl:value-of select="$baseUrn"
            /><xsl:value-of select="ancestor::tei:div1/@n"/>.<xsl:value-of
            select="substring-before(@synch,'-')"/>-<xsl:value-of select="ancestor::tei:div1/@n"
            />.<xsl:value-of select="substring-after(@synch,'-')"/><xsl:text>
</xsl:text>
    </xsl:template>


</xsl:stylesheet>
