<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="html" indent="no" />
	<xsl:variable name="nColumns" select="3" />

	<xsl:template match="/">

		<xsl:element name="script">
			<xsl:attribute name="type">text/javascript</xsl:attribute>
			<xsl:text>function showCountry(country) {</xsl:text>
			<xsl:for-each select="/stockists/country">
				<xsl:text>document.getElementById('</xsl:text>
				<xsl:value-of select="@name" />
				<xsl:text>').style.display = 'none';</xsl:text>
			</xsl:for-each>
			<xsl:text>document.getElementById(country).style.display = 'block';}</xsl:text>
		</xsl:element>

		<xsl:element name="noscript">
			<xsl:element name="div">
				<xsl:attribute name="style">font-size: 120%; font-family: monospace; color: red; margin: 8px 0px;</xsl:attribute>
				<xsl:text>Please enable JavaScript to view stockists from other countries</xsl:text>
			</xsl:element>
		</xsl:element>

		<xsl:element name="h1">
			<xsl:text>Hibou Home Stockists</xsl:text>
		</xsl:element>
		<xsl:element name="p">
			<xsl:text>Please click on a country to view our stockists in that particular country.</xsl:text>
		</xsl:element>

		<xsl:element name="table">
			<xsl:attribute name="style">border: none</xsl:attribute>
			<xsl:element name="tr">
				<xsl:attribute name="style">border: none</xsl:attribute>
				<xsl:element name="td">
					<xsl:attribute name="style">width: 150px; vertical-align: top; border: none; background-color: white</xsl:attribute>
					<xsl:call-template name="menu" />
				</xsl:element>
				<xsl:element name="td">
					<xsl:attribute name="style">vertical-align: top; border: none; background-color: white;</xsl:attribute>
					<xsl:call-template name="countries" />
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>

	<xsl:template name="menu">
		<xsl:element name="ul">
			<xsl:attribute name="style">list-style-type: none; padding: 0px; margin: 7px 12px 0px 0px;</xsl:attribute>
			<xsl:for-each select="/stockists/country">
				<xsl:element name="li">
					<xsl:choose>
						<xsl:when test="position() = last()">
							<xsl:attribute name="style">padding: 3px 3px 3px 6px; border-style: solid solid solid solid; border-width: 1px; border-color: #E6E1CC;</xsl:attribute>
						</xsl:when>
						<xsl:otherwise>
							<xsl:attribute name="style">padding: 3px 3px 3px 6px; border-style: solid solid none solid; border-width: 1px; border-color: #E6E1CC;</xsl:attribute>
						</xsl:otherwise>
					</xsl:choose>
					<xsl:element name="a">
						<xsl:attribute name="href">javascript:showCountry('<xsl:value-of select="@name" />')</xsl:attribute>
						<xsl:attribute name="style">color: #797272; font-size: 120%; font-weight: bold;</xsl:attribute>
						<xsl:value-of select="@name" />
					</xsl:element>
				</xsl:element>
			</xsl:for-each>
		</xsl:element>
	</xsl:template>

	<xsl:template name="countries">
		<xsl:for-each select="/stockists/country">
			<xsl:element name="div">
				<xsl:attribute name="id"><xsl:value-of select="@name" /></xsl:attribute>
				<xsl:attribute name="style">margin-top: 10px; display:<xsl:choose><xsl:when test="@name = 'United Kingdom'">block</xsl:when><xsl:otherwise>none</xsl:otherwise></xsl:choose>;</xsl:attribute>
				<!-- country name -->
				<xsl:element name="div">
					<xsl:attribute name="style">font-size: 140%; font-weight: bold; margin-bottom: 24px;</xsl:attribute>
					<xsl:value-of select="@name" />
				</xsl:element>
				<!-- Check if the country has any stockists -->
				<xsl:if test="not(./region) and not(./stockist)">
					<xsl:text>Please </xsl:text>
					<xsl:element name="a">
						<xsl:attribute name="href">mailto:info@hibouhome.com</xsl:attribute>
						<xsl:text>contact</xsl:text>
					</xsl:element>
					<xsl:text> us for a list of our stockists in </xsl:text>
					<xsl:value-of select="@name" />
				</xsl:if>
				<!-- regions -->
				<xsl:call-template name="regions">
					<xsl:with-param name="regions" select="./region" />
				</xsl:call-template>
				<!-- region-less stockists -->
				<xsl:call-template name="stockists">
					<xsl:with-param name="stockists" select="./stockist" />
				</xsl:call-template>
			</xsl:element>
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="regions">
		<xsl:param name="regions" />
		<xsl:for-each select="$regions">
			<xsl:element name="div">
				<xsl:attribute name="style">font-size: 120%; font-weight: bold; border-bottom: 1px solid #E6E1CC;</xsl:attribute>
				<xsl:value-of select="./@name" />
			</xsl:element>
			<xsl:call-template name="stockists">
				<xsl:with-param name="stockists" select="./stockist" />
			</xsl:call-template>
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="stockists">
		<xsl:param name="stockists" />
		<xsl:element name="table">
			<xsl:attribute name="style">border: none; padding: 0px; margin: 10px 0px 0px 0px;</xsl:attribute>
			<xsl:call-template name="table-rows">
				<xsl:with-param name="items" select="$stockists" />
			</xsl:call-template>
		</xsl:element>
	</xsl:template>

	<xsl:template name="stockist">
		<xsl:param name="stockist" />
		<!-- name -->
		<xsl:element name="span">
			<xsl:attribute name="style">font-weight: bold; padding-bottom: 5px;</xsl:attribute>
			<xsl:value-of select="$stockist/@name" />
		</xsl:element>
		<!-- address -->
		<xsl:if test="$stockist/address-line">
			<xsl:element name="div">
				<xsl:attribute name="style">margin-top: 8px;</xsl:attribute>
				<xsl:for-each select="$stockist/address-line">
					<xsl:value-of select="." />
					<xsl:if test="position() != last()">
						<xsl:element name="br" />
					</xsl:if>
				</xsl:for-each>
			</xsl:element>
		</xsl:if>
		<!-- contact details -->
		<xsl:if test="$stockist/telephone or $stockist/fax or $stockist/email or $stockist/website">
			<xsl:element name="div">
				<xsl:attribute name="style">margin-top: 8px;</xsl:attribute>
				<!-- telephone -->
				<xsl:if test="$stockist/telephone">
					<xsl:text disable-output-escaping="yes">T:<![CDATA[&nbsp;]]></xsl:text>
					<xsl:value-of select="$stockist/telephone" />
				</xsl:if>
				<!-- fax -->
				<xsl:if test="$stockist/fax">
					<xsl:if test="$stockist/telephone">
						<xsl:element name="br" />
					</xsl:if>
					<xsl:text disable-output-escaping="yes">F:<![CDATA[&nbsp;]]></xsl:text>
					<xsl:value-of select="$stockist/fax" />
				</xsl:if>
				<!-- email -->
				<xsl:if test="$stockist/email">
					<xsl:if test="$stockist/telephone or $stockist/fax">
						<xsl:element name="br" />
					</xsl:if>
					<xsl:text disable-output-escaping="yes">E:<![CDATA[&nbsp;]]></xsl:text>
					<xsl:element name="a">
						<xsl:attribute name="href">mailto:<xsl:value-of select="$stockist/email" /></xsl:attribute>
						<xsl:value-of select="$stockist/email" />
					</xsl:element>
				</xsl:if>
			</xsl:element>
		</xsl:if>
		<!-- website -->
		<xsl:if test="$stockist/website">
			<xsl:element name="div">
				<xsl:attribute name="style">margin-top: 8px;</xsl:attribute>
				<xsl:element name="a">
					<xsl:attribute name="href">http://<xsl:value-of select="$stockist/website" /></xsl:attribute>
					<xsl:attribute name="target">_blank</xsl:attribute>
					<xsl:value-of select="$stockist/website" />
				</xsl:element>
			</xsl:element>
		</xsl:if>

	</xsl:template>

	<xsl:template name="table-rows">
		<xsl:param name="items" />
		<xsl:param name="row" select="1" />
		<xsl:variable name="nRows" select="ceiling(count($items) div $nColumns)"></xsl:variable>
		<xsl:element name="tr">
			<xsl:call-template name="table-columns">
				<xsl:with-param name="items" select="$items" />
				<xsl:with-param name="row" select="$row" />
			</xsl:call-template>
		</xsl:element>
		<xsl:if test="$nRows > $row">
			<xsl:call-template name="table-rows">
				<xsl:with-param name="items" select="$items" />
				<xsl:with-param name="row" select="$row + 1" />
			</xsl:call-template>
		</xsl:if>
	</xsl:template>

	<xsl:template name="table-columns">
		<xsl:param name="items" />
		<xsl:param name="row" />
		<xsl:param name="column" select="1" />
		<xsl:variable name="itemIndex" select="(($row - 1) * $nColumns) + $column" />
		<xsl:element name="td">
			<xsl:attribute name="style">border: none; background-color: white; padding: 0px 0px 34px 0px; width: 33%;</xsl:attribute>
			<xsl:if test="count($items) >= $itemIndex">
				<xsl:call-template name="stockist">
					<xsl:with-param name="stockist" select="$items[$itemIndex]" />
				</xsl:call-template>
			</xsl:if>
		</xsl:element>
		<xsl:if test="$nColumns > $column">
			<xsl:call-template name="table-columns">
				<xsl:with-param name="items" select="$items" />
				<xsl:with-param name="row" select="$row" />
				<xsl:with-param name="column" select="$column + 1" />
			</xsl:call-template>
		</xsl:if>
	</xsl:template>

</xsl:stylesheet>