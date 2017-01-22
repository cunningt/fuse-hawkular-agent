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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for operationParamDmr complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="operationParamDmr">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="type" type="{urn:org.hawkular.agent:agent:1.0}operationParamTypeType" default="string" />
 *       &lt;attribute name="default-value" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="required" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="description" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "operationParamDmr")
public class OperationParamDmr {

    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "type")
    protected OperationParamTypeType type;
    @XmlAttribute(name = "default-value")
    protected String defaultValue;
    @XmlAttribute(name = "required")
    protected Boolean required;
    @XmlAttribute(name = "description")
    protected String description;

    /**
     * Gets the value of the name property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the type property.
     *
     * @return
     *     possible object is
     *     {@link OperationParamTypeType }
     *
     */
    public OperationParamTypeType getType() {
        if (type == null) {
            return OperationParamTypeType.STRING;
        } else {
            return type;
        }
    }

    /**
     * Sets the value of the type property.
     *
     * @param value
     *     allowed object is
     *     {@link OperationParamTypeType }
     *
     */
    public void setType(OperationParamTypeType value) {
        this.type = value;
    }

    /**
     * Gets the value of the defaultValue property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * Sets the value of the defaultValue property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDefaultValue(String value) {
        this.defaultValue = value;
    }

    /**
     * Gets the value of the required property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public boolean isRequired() {
        if (required == null) {
            return false;
        } else {
            return required;
        }
    }

    /**
     * Sets the value of the required property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setRequired(Boolean value) {
        this.required = value;
    }

    /**
     * Gets the value of the description property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDescription(String value) {
        this.description = value;
    }

}
