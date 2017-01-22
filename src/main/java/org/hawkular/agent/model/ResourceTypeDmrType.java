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

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resourceTypeDmrType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="resourceTypeDmrType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resource-config-dmr" type="{urn:org.hawkular.agent:agent:1.0}resourceConfigDmrType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="operation-dmr" type="{urn:org.hawkular.agent:agent:1.0}operationDmrType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="resource-name-template" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="path" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="parents" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="metric-sets" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="avail-sets" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resourceTypeDmrType", propOrder = {
    "resourceConfigDmr",
    "operationDmr"
})
public class ResourceTypeDmrType {

    @XmlElement(name = "resource-config-dmr")
    protected List<ResourceConfigDmrType> resourceConfigDmr;
    @XmlElement(name = "operation-dmr")
    protected List<OperationDmrType> operationDmr;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "resource-name-template", required = true)
    protected String resourceNameTemplate;
    @XmlAttribute(name = "path", required = true)
    protected String path;
    @XmlAttribute(name = "parents")
    protected String parents;
    @XmlAttribute(name = "metric-sets")
    protected String metricSets;
    @XmlAttribute(name = "avail-sets")
    protected String availSets;

    /**
     * Gets the value of the resourceConfigDmr property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the resourceConfigDmr property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResourceConfigDmr().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ResourceConfigDmrType }
     *
     *
     */
    public List<ResourceConfigDmrType> getResourceConfigDmr() {
        if (resourceConfigDmr == null) {
            resourceConfigDmr = new ArrayList<ResourceConfigDmrType>();
        }
        return this.resourceConfigDmr;
    }

    /**
     * Gets the value of the operationDmr property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the operationDmr property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOperationDmr().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OperationDmrType }
     *
     *
     */
    public List<OperationDmrType> getOperationDmr() {
        if (operationDmr == null) {
            operationDmr = new ArrayList<OperationDmrType>();
        }
        return this.operationDmr;
    }

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
     * Gets the value of the resourceNameTemplate property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getResourceNameTemplate() {
        return resourceNameTemplate;
    }

    /**
     * Sets the value of the resourceNameTemplate property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setResourceNameTemplate(String value) {
        this.resourceNameTemplate = value;
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
     * Gets the value of the parents property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getParents() {
        return parents;
    }

    /**
     * Sets the value of the parents property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setParents(String value) {
        this.parents = value;
    }

    /**
     * Gets the value of the metricSets property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMetricSets() {
        return metricSets;
    }

    /**
     * Sets the value of the metricSets property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setMetricSets(String value) {
        this.metricSets = value;
    }

    /**
     * Gets the value of the availSets property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAvailSets() {
        return availSets;
    }

    /**
     * Sets the value of the availSets property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAvailSets(String value) {
        this.availSets = value;
    }

}
