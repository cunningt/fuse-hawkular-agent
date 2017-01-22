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
 * <p>Java class for availJmxType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="availJmxType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="object-name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="attribute" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="interval" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="time-units" type="{urn:org.hawkular.agent:agent:1.0}timeUnitsType" />
 *       &lt;attribute name="up-regex" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="metric-id-template" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="metric-tags" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "availJmxType")
public class AvailJmxType {

    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "object-name")
    protected String objectName;
    @XmlAttribute(name = "attribute")
    protected String attribute;
    @XmlAttribute(name = "interval")
    protected Integer interval;
    @XmlAttribute(name = "time-units")
    protected TimeUnitsType timeUnits;
    @XmlAttribute(name = "up-regex")
    protected String upRegex;
    @XmlAttribute(name = "metric-id-template")
    protected String metricIdTemplate;
    @XmlAttribute(name = "metric-tags")
    protected String metricTags;

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
     * Gets the value of the objectName property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getObjectName() {
        return objectName;
    }

    /**
     * Sets the value of the objectName property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setObjectName(String value) {
        this.objectName = value;
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
     * Gets the value of the interval property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getInterval() {
        return interval;
    }

    /**
     * Sets the value of the interval property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setInterval(Integer value) {
        this.interval = value;
    }

    /**
     * Gets the value of the timeUnits property.
     *
     * @return
     *     possible object is
     *     {@link TimeUnitsType }
     *
     */
    public TimeUnitsType getTimeUnits() {
        return timeUnits;
    }

    /**
     * Sets the value of the timeUnits property.
     *
     * @param value
     *     allowed object is
     *     {@link TimeUnitsType }
     *
     */
    public void setTimeUnits(TimeUnitsType value) {
        this.timeUnits = value;
    }

    /**
     * Gets the value of the upRegex property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getUpRegex() {
        return upRegex;
    }

    /**
     * Sets the value of the upRegex property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setUpRegex(String value) {
        this.upRegex = value;
    }

    /**
     * Gets the value of the metricIdTemplate property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMetricIdTemplate() {
        return metricIdTemplate;
    }

    /**
     * Sets the value of the metricIdTemplate property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setMetricIdTemplate(String value) {
        this.metricIdTemplate = value;
    }

    /**
     * Gets the value of the metricTags property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMetricTags() {
        return metricTags;
    }

    /**
     * Sets the value of the metricTags property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setMetricTags(String value) {
        this.metricTags = value;
    }

}
