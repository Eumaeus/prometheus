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
   

    
   
    
    <xsl:template match="tei:body//tei:l">
        <xsl:element name="l">
            <xsl:if test="@n">
                <xsl:attribute name="n"><xsl:value-of select="@n"/></xsl:attribute>
            </xsl:if>
            <xsl:call-template name="tokenize">
                <xsl:with-param name="lineText"><xsl:value-of select="substring-after(.,' ')"/></xsl:with-param>
                
            </xsl:call-template>
        </xsl:element>
    </xsl:template>
    
    
    <xsl:template name="tokenize">
        <xsl:param name="lineText"/>
        
        <xsl:variable name="restOfLine"><xsl:value-of select="substring-after($lineText,' ')"/></xsl:variable>
        <xsl:variable name="firstToken"><xsl:value-of select="substring-before($lineText,' ')"/></xsl:variable>
        <xsl:if test="$firstToken != ''">
        
       <xsl:element name="w">
           
            <xsl:value-of select="$firstToken"/>
        </xsl:element>
        
        </xsl:if>
        <xsl:text> </xsl:text>
       
        <xsl:if test="($restOfLine != ' ') and ($restOfLine != '')">
            
        <xsl:call-template name="tokenize">
            <xsl:with-param name="lineText"><xsl:value-of select="$restOfLine"/></xsl:with-param>
            
        </xsl:call-template>
        </xsl:if>
    </xsl:template>
    
    
    
    
    
    <xsl:template match="@*|node()" priority="-1">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>
</xsl:stylesheet>
