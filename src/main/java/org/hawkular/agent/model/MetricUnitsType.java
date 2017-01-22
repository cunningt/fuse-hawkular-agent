/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hawkular.agent.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for metricUnitsType.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="metricUnitsType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="bytes"/>
 *     &lt;enumeration value="kilobytes"/>
 *     &lt;enumeration value="megabytes"/>
 *     &lt;enumeration value="gigabytes"/>
 *     &lt;enumeration value="terabytes"/>
 *     &lt;enumeration value="milliseconds"/>
 *     &lt;enumeration value="seconds"/>
 *     &lt;enumeration value="minutes"/>
 *     &lt;enumeration value="none"/>
 *     &lt;enumeration value="percentage"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "metricUnitsType")
@XmlEnum
public enum MetricUnitsType {

    @XmlEnumValue("bytes")
    BYTES("bytes"),
    @XmlEnumValue("kilobytes")
    KILOBYTES("kilobytes"),
    @XmlEnumValue("megabytes")
    MEGABYTES("megabytes"),
    @XmlEnumValue("gigabytes")
    GIGABYTES("gigabytes"),
    @XmlEnumValue("terabytes")
    TERABYTES("terabytes"),
    @XmlEnumValue("milliseconds")
    MILLISECONDS("milliseconds"),
    @XmlEnumValue("seconds")
    SECONDS("seconds"),
    @XmlEnumValue("minutes")
    MINUTES("minutes"),
    @XmlEnumValue("none")
    NONE("none"),
    @XmlEnumValue("percentage")
    PERCENTAGE("percentage");
    private final String value;

    MetricUnitsType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MetricUnitsType fromValue(String v) {
        for (MetricUnitsType c: MetricUnitsType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
