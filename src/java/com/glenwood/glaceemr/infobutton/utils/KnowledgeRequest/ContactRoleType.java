//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.11.30 at 11:18:11 PM EST 
//


package com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContactRoleType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ContactRoleType">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="ECON"/>
 *     &lt;enumeration value="NOK"/>
 *     &lt;enumeration value="BILL"/>
 *     &lt;enumeration value="ORG"/>
 *     &lt;enumeration value="PAYOR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ContactRoleType")
@XmlEnum
public enum ContactRoleType {

    ECON,
    NOK,
    BILL,
    ORG,
    PAYOR;

    public String value() {
        return name();
    }

    public static ContactRoleType fromValue(String v) {
        return valueOf(v);
    }

}
