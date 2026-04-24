<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                version="1.0" xmlns:xs="http://www.w3.org/1999/XSL/Transform">
    <xsl:output encoding="UTF-8" indent="yes" method="xml" standalone="no" omit-xml-declaration="no"/>
    <xsl:template match="/">
        <fo:root language="EN">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="A4-portrait" page-heigh="297mm"
                                       page-width="210mm" margin-top="5mm"
                                       martin-bottom="5mm" margin-left="5mm"
                                       margin-right="5mm">
                    <fo:region-body margin-top="25mm" margin-bottom="20mm"/>
                    <fo:region-before region-name="xsl-region-before" extent="25mm"
                                      display-aling="before" precedence="true"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="A4-portrait">
                <fo:flow flow-name="xsl-region-body" border-collapse="collapse"> reference-orientation="0">
                    <fo:block font-size="14pt" font-weight="bold" text-align="center">
                        Drone Sales Report
                    </fo:block>
                    <fo:block text-align="center">
                        <fo:table table-layout="fixed" width="80%" font-size="10pt">
                            <fo:table-column column-width="proportional-column-width(5)"/>
                            <fo:table-column column-width="proportional-column-width(25)"/>
                            <fo:table-column column-width="proportional-column-width(20)"/>
                            <fo:table-column column-width="proportional-column-width(50)"/>
                            <fo:table-body>
                                <fo:table-row height="8mm" border-color="grey">
                                    <fo:table-cell>
                                        <fo:block>Id</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block>Account Number</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block>Manager</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block>Sold Drones</fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <xsl:for-each select="magagers/manager">
                                    <fo:table-row>
                                    <fo:table-cell><xsl:value-of select="id"/></fo:table-cell>
                                    <fo:table-cell><xsl:value-of select="accountNumber"/></fo:table-cell>
                                    <fo:table-cell><xsl:value-of select="username"/></fo:table-cell>
                                    <fo:table-cell>
                                        <xsl:choose>
                                            <xsl:when test="soldDrones/drone">
                                                <fo:table>
                                                    <fo:table-row>
                                                        <fo:table-cell>
                                                            <fo:block>Drone Name</fo:block>
                                                        </fo:table-cell>
                                                        <fo:table-cell>
                                                            <fo:block>Price</fo:block>
                                                        </fo:table-cell>
                                                        <fo:table-cell>
                                                            <fo:block>Battery</fo:block>
                                                        </fo:table-cell>
                                                    </fo:table-row>
                                                    <xs:for-each select="soldDrones/drone">
                                                        <fo:table-row>
                                                            <fo:table-cell>
                                                                <fo:block><xsl:value-of select="droneName"/></fo:block>
                                                            </fo:table-cell>
                                                            <fo:table-cell>
                                                                <fo:block><xsl:value-of select="price"/></fo:block>
                                                            </fo:table-cell>
                                                            <fo:table-cell>
                                                                <fo:block><xsl:value-of select="batteryCapacity"/></fo:block>
                                                            </fo:table-cell>
                                                        </fo:table-row>
                                                    </xs:for-each>
                                                </fo:table>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <fo:block>No drones sold yet.</fo:block>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </fo:table-cell>
                                    </fo:table-row>
                                </xsl:for-each>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>
                    <fo:block>
                        <fo:table table-layout="fixed" width="80%" font-size="10pt">
                            <fo:table-body>
                                <fo:table-row height="8mm" border-color="grey">
                                    <fo:table-cell>
                                        <fo:block>Totally sold drones - <xsl:value-of select="count(//drone)"/></fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block>Totally earned - <xsl:value-of select="sum(//soldDrones/drone/price)"/>$</fo:block>
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