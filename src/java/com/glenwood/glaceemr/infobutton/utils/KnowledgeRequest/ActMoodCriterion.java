//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.11.30 at 11:18:11 PM EST 
//


package com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ActMoodCriterion.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ActMoodCriterion">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="CRT"/>
 *     &lt;enumeration value="EVN.CRT"/>
 *     &lt;enumeration value="GOL.CRT"/>
 *     &lt;enumeration value="INT.CRT"/>
 *     &lt;enumeration value="PRMS.CRT"/>
 *     &lt;enumeration value="RQO.CRT"/>
 *     &lt;enumeration value="RSK.CRT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ActMoodCriterion")
@XmlEnum
public enum ActMoodCriterion {

    CRT("CRT"),
    @XmlEnumValue("EVN.CRT")
    EVN_CRT("EVN.CRT"),
    @XmlEnumValue("GOL.CRT")
    GOL_CRT("GOL.CRT"),
    @XmlEnumValue("INT.CRT")
    INT_CRT("INT.CRT"),
    @XmlEnumValue("PRMS.CRT")
    PRMS_CRT("PRMS.CRT"),
    @XmlEnumValue("RQO.CRT")
    RQO_CRT("RQO.CRT"),
    @XmlEnumValue("RSK.CRT")
    RSK_CRT("RSK.CRT");
    private final String value;

    ActMoodCriterion(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ActMoodCriterion fromValue(String v) {
        for (ActMoodCriterion c: ActMoodCriterion.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
