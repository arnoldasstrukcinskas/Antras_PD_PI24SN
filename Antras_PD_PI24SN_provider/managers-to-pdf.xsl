<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                version="1.0">
    <xsl:output encoding="UTF-8" indent="yes" method="xml" standalone="no" omit-xml-declaration="no"/>

    <xsl:template match="/">
        <fo:root language="EN">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="A4-portrait" page-height="297mm"
                                       page-width="210mm" margin-top="15mm"
                                       margin-bottom="15mm" margin-left="15mm"
                                       margin-right="15mm">
                    <fo:region-body margin-top="20mm"/>
                    <fo:region-before extent="15mm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="A4-portrait">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-size="18pt" font-family="sans-serif" font-weight="bold"
                              text-align="center" space-after="20pt" color="#2E4053">
                        Drone Sales Report
                    </fo:block>

                    <fo:table table-layout="fixed" width="100%" border="0.5pt solid #AEB6BF">
                        <fo:table-column column-width="10%"/>
                        <fo:table-column column-width="30%"/>
                        <fo:table-column column-width="25%"/>
                        <fo:table-column column-width="35%"/>

                        <fo:table-header>
                            <fo:table-row background-color="#2E4053" color="white" font-weight="bold">
                                <fo:table-cell padding="4pt" border="0.5pt solid #AEB6BF"><fo:block>ID</fo:block></fo:table-cell>
                                <fo:table-cell padding="4pt" border="0.5pt solid #AEB6BF"><fo:block>Account Number</fo:block></fo:table-cell>
                                <fo:table-cell padding="4pt" border="0.5pt solid #AEB6BF"><fo:block>Manager</fo:block></fo:table-cell>
                                <fo:table-cell padding="4pt" border="0.5pt solid #AEB6BF"><fo:block>Sold Drones</fo:block></fo:table-cell>
                            </fo:table-row>
                        </fo:table-header>

                        <fo:table-body>
                            <xsl:for-each select="//manager">
                                <fo:table-row>
                                    <xsl:if test="position() mod 2 = 0">
                                        <xsl:attribute name="background-color">
                                            <xsl:text>#F2F4F4</xsl:text>
                                        </xsl:attribute>
                                    </xsl:if>

                                    <fo:table-cell padding="5pt" border="0.5pt solid #D5DBDB">
                                        <fo:block><xsl:value-of select="id"/></fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell padding="5pt" border="0.5pt solid #D5DBDB">
                                        <fo:block><xsl:value-of select="accountNumber"/></fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell padding="5pt" border="0.5pt solid #D5DBDB">
                                        <fo:block font-weight="bold"><xsl:value-of select="username"/></fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell padding="5pt" border="0.5pt solid #D5DBDB">
                                        <xsl:choose>
                                            <xsl:when test="soldDrones/drone">
                                                <xsl:for-each select="soldDrones/drone">
                                                    <fo:block font-size="9pt" border-bottom="0.2pt solid #EBEDEF" margin-bottom="2pt">
                                                        • <xsl:value-of select="droneName"/>
                                                        (<xsl:value-of select="price"/>$)
                                                    </fo:block>
                                                </xsl:for-each>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <fo:block font-size="9pt" color="gray" font-style="italic">No sales</fo:block>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </fo:table-cell>
                                </fo:table-row>
                            </xsl:for-each>
                        </fo:table-body>
                    </fo:table>

                    <fo:block space-before="15pt" font-family="sans-serif" border-top="1pt solid #2E4053" padding-top="5pt">
                        <fo:table table-layout="fixed" width="100%">
                            <fo:table-body>
                                <fo:table-row font-weight="bold" font-size="11pt">
                                    <fo:table-cell>
                                        <fo:block>Total Drones Sold: <xsl:value-of select="count(//drone)"/></fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell text-align="right">
                                        <fo:block color="#1E8449">Total Revenue: <xsl:value-of select="sum(//drone/price)"/>$</fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>