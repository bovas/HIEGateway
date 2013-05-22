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
 * <p>Java class for ActMood.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ActMood">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="APT"/>
 *     &lt;enumeration value="ARQ"/>
 *     &lt;enumeration value="CRT"/>
 *     &lt;enumeration value="DEF"/>
 *     &lt;enumeration value="EVN"/>
 *     &lt;enumeration value="EVN.CRT"/>
 *     &lt;enumeration value="EXPEC"/>
 *     &lt;enumeration value="GOL"/>
 *     &lt;enumeration value="GOL.CRT"/>
 *     &lt;enumeration value="INT"/>
 *     &lt;enumeration value="INT.CRT"/>
 *     &lt;enumeration value="OPT"/>
 *     &lt;enumeration value="PERM"/>
 *     &lt;enumeration value="PERMRQ"/>
 *     &lt;enumeration value="PRMS"/>
 *     &lt;enumeration value="PRMS.CRT"/>
 *     &lt;enumeration value="PRP"/>
 *     &lt;enumeration value="RMD"/>
 *     &lt;enumeration value="RQO"/>
 *     &lt;enumeration value="RQO.CRT"/>
 *     &lt;enumeration value="RSK"/>
 *     &lt;enumeration value="RSK.CRT"/>
 *     &lt;enumeration value="SLOT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ActMood")
@XmlEnum
public enum ActMood {

    APT("APT"),
    ARQ("ARQ"),
    CRT("CRT"),
    DEF("DEF"),
    EVN("EVN"),
    @XmlEnumValue("EVN.CRT")
    EVN_CRT("EVN.CRT"),
    EXPEC("EXPEC"),
    GOL("GOL"),
    @XmlEnumValue("GOL.CRT")
    GOL_CRT("GOL.CRT"),
    INT("INT"),
    @XmlEnumValue("INT.CRT")
    INT_CRT("INT.CRT"),
    OPT("OPT"),
    PERM("PERM"),
    PERMRQ("PERMRQ"),
    PRMS("PRMS"),
    @XmlEnumValue("PRMS.CRT")
    PRMS_CRT("PRMS.CRT"),
    PRP("PRP"),
    RMD("RMD"),
    RQO("RQO"),
    @XmlEnumValue("RQO.CRT")
    RQO_CRT("RQO.CRT"),
    RSK("RSK"),
    @XmlEnumValue("RSK.CRT")
    RSK_CRT("RSK.CRT"),
    SLOT("SLOT");
    private final String value;

    ActMood(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ActMood fromValue(String v) {
        for (ActMood c: ActMood.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
