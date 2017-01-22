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
 * <p>Java class for operationParamTypeType.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="operationParamTypeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="string"/>
 *     &lt;enumeration value="bool"/>
 *     &lt;enumeration value="int"/>
 *     &lt;enumeration value="float"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "operationParamTypeType")
@XmlEnum
public enum OperationParamTypeType {

    @XmlEnumValue("string")
    STRING("string"),
    @XmlEnumValue("bool")
    BOOL("bool"),
    @XmlEnumValue("int")
    INT("int"),
    @XmlEnumValue("float")
    FLOAT("float");
    private final String value;

    OperationParamTypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static OperationParamTypeType fromValue(String v) {
        for (OperationParamTypeType c: OperationParamTypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
