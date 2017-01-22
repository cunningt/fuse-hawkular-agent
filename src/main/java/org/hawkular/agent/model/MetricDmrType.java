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
 * <p>Java class for metricDmrType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="metricDmrType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="path" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="attribute" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="resolve-expressions" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="include-defaults" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="metric-type" type="{urn:org.hawkular.agent:agent:1.0}metricTypeType" />
 *       &lt;attribute name="metric-units" type="{urn:org.hawkular.agent:agent:1.0}metricUnitsType" />
 *       &lt;attribute name="interval" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="time-units" type="{urn:org.hawkular.agent:agent:1.0}timeUnitsType" />
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
@XmlType(name = "metricDmrType")
public class MetricDmrType {

    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "path", required = true)
    protected String path;
    @XmlAttribute(name = "attribute", required = true)
    protected String attribute;
    @XmlAttribute(name = "resolve-expressions")
    protected Boolean resolveExpressions;
    @XmlAttribute(name = "include-defaults")
    protected Boolean includeDefaults;
    @XmlAttribute(name = "metric-type")
    protected MetricTypeType metricType;
    @XmlAttribute(name = "metric-units")
    protected MetricUnitsType metricUnits;
    @XmlAttribute(name = "interval")
    protected Integer interval;
    @XmlAttribute(name = "time-units")
    protected TimeUnitsType timeUnits;
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

    /**
     * Gets the value of the metricType property.
     *
     * @return
     *     possible object is
     *     {@link MetricTypeType }
     *
     */
    public MetricTypeType getMetricType() {
        return metricType;
    }

    /**
     * Sets the value of the metricType property.
     *
     * @param value
     *     allowed object is
     *     {@link MetricTypeType }
     *
     */
    public void setMetricType(MetricTypeType value) {
        this.metricType = value;
    }

    /**
     * Gets the value of the metricUnits property.
     *
     * @return
     *     possible object is
     *     {@link MetricUnitsType }
     *
     */
    public MetricUnitsType getMetricUnits() {
        return metricUnits;
    }

    /**
     * Sets the value of the metricUnits property.
     *
     * @param value
     *     allowed object is
     *     {@link MetricUnitsType }
     *
     */
    public void setMetricUnits(MetricUnitsType value) {
        this.metricUnits = value;
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
