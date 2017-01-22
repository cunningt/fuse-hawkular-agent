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
 * <p>Java class for resourceConfigDmrType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="resourceConfigDmrType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="path" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="attribute" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="resolve-expressions" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="include-defaults" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resourceConfigDmrType")
public class ResourceConfigDmrType {

    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "path")
    protected String path;
    @XmlAttribute(name = "attribute", required = true)
    protected String attribute;
    @XmlAttribute(name = "resolve-expressions")
    protected Boolean resolveExpressions;
    @XmlAttribute(name = "include-defaults")
    protected Boolean includeDefaults;

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
     * Gets the value of the path property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the value of the path property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPath(String value) {
        this.path = value;
    }

    /**
     * Gets the value of the attribute property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAttribute() {
        return attribute;
    }

    /**
     * Sets the value of the attribute property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAttribute(String value) {
        this.attribute = value;
    }

    /**
     * Gets the value of the resolveExpressions property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean isResolveExpressions() {
        return resolveExpressions;
    }

    /**
     * Sets the value of the resolveExpressions property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setResolveExpressions(Boolean value) {
        this.resolveExpressions = value;
    }

    /**
     * Gets the value of the includeDefaults property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean isIncludeDefaults() {
        return includeDefaults;
    }

    /**
     * Sets the value of the includeDefaults property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setIncludeDefaults(Boolean value) {
        this.includeDefaults = value;
    }

}
